import numpy as np
import matplotlib.pyplot as plt
import random as rand

class GridWorld:

    # north, east, south, west
    directions = [
            (-1, 0),
            (0, 1),
            (1, 0),
            (0, -1),
    ]
    num_moves = len(directions)

    def __init__(self, prize_grid, trm_msk, wall_mask,
                     move_probs,
                     no_move_prob):

            self._prize_grid = prize_grid
            self._trm_msk = trm_msk
            self._wall_mask = wall_mask
            self._T = self._make_trans_matrix(
                move_probs,
                no_move_prob,
                wall_mask
            )

    @property
    def shape(self):
            return self._prize_grid.shape

        @property
        def size(self):
            return self._prize_grid.size

        @property
        def prize_grid(self):
            return self._prize_grid

        def run_val_iters(self, discount=.9,
                                 iters=10):
            util_grids, policy_grids = self._init_util_policy_storage(iters)

            util_grid = np.zeros_like(self._prize_grid)
            for i in range(iters):
                util_grid = self._val_iter(util_grid=util_grid)
                policy_grids[:, :, i] = self.optimal_policy(util_grid)
                util_grids[:, :, i] = util_grid
            return policy_grids, util_grids

        def run_policy_iters(self, discount=.9,
                                  iters=10):
            util_grids, policy_grids = self._init_util_policy_storage(iters)

            policy_grid = np.random.randint(0, self._num_moves,
                                            self.shape)
            util_grid = self._prize_grid.copy()

            for i in range(iters):
                policy_grid, util_grid = self._policy_iter(
                    policy_grid=policy_grid,
                    util_grid=util_grid
                )
                policy_grids[:, :, i] = policy_grid
                util_grids[:, :, i] = util_grid
            return policy_grids, util_grids

        def gen_exp(self, current_state_idx, move_idx):
            A, B = self.grid_indices_to_coords(current_state_idx)
            next_state_probs = self._T[A, B, move_idx, :, :].flatten()
            next_state_idx = np.random.choice(np.arange(next_state_probs.size),
                                              p=next_state_probs)

            return (next_state_idx,
                    self._prize_grid.flatten()[next_state_idx],
                    self._trm_msk.flatten()[next_state_idx])

        def grid_indices_to_coords(self, indices=None):
            if indices is None:
                indices = np.arange(self.size)
            return np.unravel_index(indices, self.shape)

        def grid_coords_to_indices(self, coords=None):
            # only works for + indices
            if coords is None:
                return np.arange(self.size)
            return np.ravel_multi_index(coords, self.shape)

        def optimal_policy(self, util_grid):
            M, N = self.shape
            return np.argmax((util_grid.reshape((1, 1, 1, M, N)) * self._T)
                             .sum(axis=-1).sum(axis=-1), axis=2)

        def _init_util_policy_storage(self, depth):
            M, N = self.shape
            util_grids = np.zeros((M, N, depth))
            policy_grids = np.zeros_like(util_grids)
            return util_grids, policy_grids

        def _make_trans_matrix(self,
                                      move_probs,
                                      no_move_prob,
                                      wall_mask):
            M, N = self.shape

            T = np.zeros((M, N, self._num_moves, M, N))

            A, B = self.grid_indices_to_coords()

            T[A, B, :, A, B] += no_move_prob

            for move in range(self._num_moves):
                for offset, P in move_probs:
                    direction = (move + offset) % self._num_moves

                    dr, dc = self.directions[direction]
                    A1 = np.clip(A + dr, 0, M - 1)
                    B1 = np.clip(B + dc, 0, N - 1)

# Flatten puts the array into one dimension

                    temp_mask = wall_mask[A1, B1].flatten()
                    A1[temp_mask] = A[temp_mask]
                    B1[temp_mask] = B[temp_mask]

                    T[A, B, move, A1, B1] += P

            terminal_locs = np.where(self._trm_msk.flatten())[0]
            T[A[terminal_locs], B[terminal_locs], :, :, :] = 0
            return T

        def _val_iter(self, util_grid, discount=.9):
            out = np.zeros_like(util_grid)
            M, N = self.shape
            for i in range(M):
                for j in range(N):
                    out[i, j] = self._calculate_util((i, j),
                                                        discount,
                                                        util_grid)
            return out

        def _policy_iter(self, *, util_grid,
                              policy_grid, discount=.9):
            r, c = self.grid_indices_to_coords()

            M, N = self.shape

            util_grid = (
                self._prize_grid + 
                discount * ((util_grid.reshape((1, 1, 1, M, N)) * self._T)
                            .sum(axis=-1).sum(axis=-1))[r, c, policy_grid.flatten()]
                .reshape(self.shape)
            )

            util_grid[self._trm_msk] = self._prize_grid[self._trm_msk]

            return self.optimal_policy(util_grid), util_grid

        def _calculate_util(self, loc, discount, util_grid):
            if self._trm_msk[loc]:
                return self._prize_grid[loc]
            row, col = loc
            return np.max(
                discount * np.sum(
                    np.sum(self._T[row, col, :, :, :] * util_grid,
                           axis=-1),
                    axis=-1)
            ) + self._prize_grid[loc]

        def plot_policy(self, util_grid, policy_grid=None):
            if policy_grid is None:
                policy_grid = self.optimal_policy(util_grid)
            markers = "^>v<"
            marker_size = 200 // np.max(policy_grid.shape)
            marker_edge_width = marker_size // 10
            marker_fill_color = 'w'

            no_move_mask = self._trm_msk | self._wall_mask

            util_norm = (util_grid - util_grid.min()) / \
                                 (util_grid.max() - util_grid.min())

            util_norm = (255 * util_norm).astype(np.uint8)

            for i, marker in enumerate(markers):
                y, x = np.where((policy_grid == i) & np.logical_not(no_move_mask))
                plt.plot(x, y, marker, ms=marker_size, mew=marker_edge_width,
                         color=marker_fill_color)

            y, x = np.where(self._trm_msk)
            plt.plot(x, y, 'o', ms=marker_size, mew=marker_edge_width,
                     color=marker_fill_color)

            tick_step_options = np.array([1, 2, 5, 10, 20, 50, 100])
            tick_step = np.max(policy_grid.shape) / 7
            optimal_option = np.argmin(np.abs(np.log(tick_step) - np.log(tick_step_options)))
            tick_step = tick_step_options[optimal_option]
            plt.xticks(np.arange(0, policy_grid.shape[1] - 0.5, tick_step))
            plt.yticks(np.arange(0, policy_grid.shape[0] - 0.5, tick_step))
            plt.xlim([-0.5, policy_grid.shape[0] - 0.5])
            plt.xlim([-0.5, policy_grid.shape[1] - 0.5])

    class QLearn:
        '''A generic implementation of Q-Learning and Dyna-Q'''

        def __init__(self, *,
                     num_states,
                     num_moves,
                     learning_rate,
                     discount_rate=.9,
                     random_move_prob=0.5,
                     random_move_decay_rate=0.99,
                     dyna_iters=0):

            self._num_states = num_states
            self._num_moves = num_moves
            self._learning_rate = learning_rate
            self._discount_rate = discount_rate
            self._random_move_prob = random_move_prob
            self._random_move_decay_rate = random_move_decay_rate
            self._dyna_iters = dyna_iters

            self._exps = []

            # Initialize Q to small random vals.
            self._Q = np.zeros((num_states, num_moves), dtype=np.float)
            self._Q += np.random.normal(0, 0.3, self._Q.shape)

        def initialize(self, state):
            '''initial state, then return the learner's move'''
            self._decide_next_move(state)
            self._stored_state = state
            return self._stored_move

        def learn(self, initial_state, exp_func, iters=100):
            '''new states and rewards update iteratively'''
            all_policies = np.zeros((self._num_states, iters))
            all_utilities = np.zeros_like(all_policies)
            for i in range(iters):
                done = False
                self.initialize(initial_state)
                for j in range(iters):
                    state, reward, done = exp_func(self._stored_state,
                                                          self._stored_move)
                    self.exp(state, reward)
                    if done:
                        break

                policy, util = self.get_policy_and_util()
                all_policies[:, i] = policy
                all_utilities[:, i] = util
            return all_policies, all_utilities

        def exp(self, state, reward):
            '''state leads to some kind of reward or prize'''
            self._update_Q(self._stored_state, self._stored_move, state, reward)
            if self._dyna_iters > 0:
                self._exps.append(
                    (self._stored_state, self._stored_move, state, reward)
                )
                exp_idx = np.random.choice(len(self._exps),
                                           self._dyna_iters)
                for i in exp_idx:
                    self._update_Q(*self._exps[i])

            # determine an move and update the current state
            self._decide_next_move(state)
            self._stored_state = state

            self._random_move_prob *= self._random_move_decay_rate

            return self._stored_move

        def get_policy_and_util(self):
            policy = np.argmax(self._Q, axis=1)
            util = np.max(self._Q, axis=1)
            return policy, util

        def _update_Q(self, s, a, s_prime, r):
            optimal_reward = self._Q[s_prime, self._find_optimal_move(s_prime)]
            self._Q[s, a] *= (1 - self._learning_rate)
            self._Q[s, a] += (self._learning_rate
                              * (r + self._discount_rate * optimal_reward))

        def _decide_next_move(self, state):
            if rand.random() <= self._random_move_prob:
                self._stored_move = rand.randint(0, self._num_moves - 1)
            else:
                self._stored_move = self._find_optimal_move(state)

        def _find_optimal_move(self, state):
            return int(np.argmax(self._Q[state, :]))

    import numpy as np
    import matplotlib.pyplot as plt

    def plot_convergence(util_grids, policy_grids):
        fig, ax1 = plt.subplots()
        ax2 = ax1.twinx()
        util_ssd = np.sum(np.square(np.diff(util_grids)), axis=(0, 1))
        ax1.plot(util_ssd, 'b.-')
        ax1.set_ylabel('Change in util', color='b')

        policy_changes = np.count_nonzero(np.diff(policy_grids), axis=(0, 1))
        ax2.plot(policy_changes, 'r.-')
        ax2.set_ylabel('Change in optimal Policy', color='r')

    if __name__ == '__main__':
        shape = (6, 6)
        goal_two = (3, 5)
        goal_three = (4, 5)
        goal = (2, 5)
        goal_one = (5, 0)
        trap = (0, 1)
        trap_one = (1, 4)
        trap_two = (5, 1)
        trap_three = (5, 4)
        trap_four = (5, 5)
        wall = (1, 3)
        wall_one = (2, 3)
        wall_two = (3, 3)
        wall_three = (5, 3)
        start = (3, 1)
        default_reward = -0.1
        goal_reward = 3
        goal_one_reward = 1
        trap_reward = -1

        prize_grid = np.zeros(shape) + default_reward
        prize_grid[goal] = goal_reward
        prize_grid[goal_two] = goal_reward
        prize_grid[goal_three] = goal_reward
        prize_grid[goal_one] = goal_one_reward
        prize_grid[trap] = trap_reward
        prize_grid[trap_one] = trap_reward
        prize_grid[trap_two] = trap_reward
        prize_grid[trap_three] = trap_reward
        prize_grid[trap_four] = trap_reward

        prize_grid[wall] = 0
        prize_grid[wall_one] = 0
        prize_grid[wall_two] = 0
        prize_grid[wall_three] = 0

        trm_msk = np.zeros_like(prize_grid, dtype=np.bool)
        trm_msk[goal] = True
        trm_msk[goal_two] = True
        trm_msk[goal_three] = True
        trm_msk[trap] = True
        trm_msk[trap_one] = True
        trm_msk[trap_two] = True
        trm_msk[trap_three] = True
        trm_msk[trap_four] = True
        trm_msk[goal_one] = True

        wall_mask = np.zeros_like(prize_grid, dtype=np.bool)
        wall_mask[1, 1] = True

        gw = GridWorld(prize_grid=prize_grid,
                          wall_mask=wall_mask,
                          trm_msk=trm_msk,
                          move_probs=[
                              (-1, 0.1),
                              (0, 0.8),
                              (1, 0.1),
                          ],
                          no_move_prob=0.0)

        mdp_solvers = {'val iter': gw.run_val_iters,
                       'Policy iter': gw.run_policy_iters}

        for solver_name, solver_fn in mdp_solvers.items():
            print('Result of {}:'.format(solver_name))
            policy_grids, util_grids = solver_fn(iters=25, discount=0.9)
            print(policy_grids[:, :, -1])
            print(util_grids[:, :, -1])
            plt.figure()
            gw.plot_policy(util_grids[:, :, -1])
            plot_convergence(util_grids, policy_grids)
            plt.show()

        ql = QLearn(num_states=(shape[0] * shape[1]),
                      num_moves=4,
                      learning_rate=0.8,
                      discount_rate=0.9,
                      random_move_prob=0.5,
                      random_move_decay_rate=0.99,
                      dyna_iters=0)

        start_state = gw.grid_coords_to_indices(start)

        iters = 1000
        flat_policies, flat_utilities = ql.learn(start_state,
                                                 gw.gen_exp,
                                                 iters=iters)

        new_shape = (gw.shape[0], gw.shape[1], iters)
        ql_util_grids = flat_utilities.reshape(new_shape)
        ql_policy_grids = flat_policies.reshape(new_shape)
        print('Result of QLearning:')
        print(ql_policy_grids[:, :, -1])
        print(ql_util_grids[:, :, -1])

        plt.figure()
        gw.plot_policy(ql_util_grids[:, :, -1], ql_policy_grids[:, :, -1])
        plot_convergence(ql_util_grids, ql_policy_grids)
        plt.show()
