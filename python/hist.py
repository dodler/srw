import cv2
import numpy as np
from matplotlib import pyplot as plt

colors=512;
img = cv2.imread('../pic/test_double.bmp', cv2.IMREAD_ANYCOLOR)
#cv2.imshow('test1', img);
hist = cv2.calcHist([img], [0], None, [colors], [0, colors])
plt.hist(img.ravel(), colors, [0,colors])
plt.title("pic hist")
plt.show()
