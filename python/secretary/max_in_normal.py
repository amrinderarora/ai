import numpy as np
import math

mu = 50
sigma = 5
print("mu: ", mu, ", sigma: ", sigma)
for x in range(14,17):
    ex = math.exp(x)
    sizeOfDist = math.floor(ex)
    s = np.random.normal(mu, sigma, sizeOfDist)
    max_s = max(s)
    print("n: ", sizeOfDist, ", log(n): ", round(math.log(sizeOfDist),4), 
          ", max:", round(max_s,4), ", log(n)/e: ", round(x/math.exp(1),4),
          ", mu + sigma log(n)/e: ", round(mu + sigma * x/math.exp(1),4))