import numpy as np
import math

mu = 0
sigma = 1
for x in range(10,17):
    ex = math.exp(x)
    sizeOfDist = math.floor(ex)
    s = np.random.normal(mu, sigma, sizeOfDist)
    max_s = max(s)
    print(sizeOfDist, round(math.log(sizeOfDist),4), round(max_s,4), round(x/math.exp(1),4))