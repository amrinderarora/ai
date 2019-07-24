def predict (x):
    print("We think it is " +  x)

print("Welcome to the Apples and Oranges Machine Learning Tool")

skin = float(input("Skin Type Smooth (1) or Rough (0): "))
cc = float(input("Enter volume in cc/ml: "))
gm = float(input("Enter weight in grams: "))

spec_density = gm/cc

# Rough skin
if skin == 0:
   if spec_density > 0.56:
       predict("orange")
   else:
       predict("apple")

if skin == 1:
   if spec_density > 0.72:
       predict("orange")
   else:
       predict("apple")

print("Thank for helping us compare apples and oranges")
