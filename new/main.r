library(png)

cat("started")

img <- readPNG(native=TRUE, "/home/dodler/Documents/hist[R]/new/img/source.png")

mean(img)
d <- dim(img)

print("image loaded")

height = d[1]
width = d[2]

data = numeric(length = height * width)

print ("calculating histogram")

cnt = 1
for (i in 1:height){
  for (j in 1:width){
    data[cnt] <- img[i,j]
    cnt <- cnt + 1
  }
}

hist(data)

print (width)
print (height)

print ("allocating mem")
data_3 = array(dim = c(width, height, width*height,3))

print ("started processing 3dim hst")

cnt = 1
for (i in 1:height){
  for(j in 1:width){
    
    for(n in 1:width){
      for(m in 1:height){
        data_3[i][j][cnt][1] <- img[i][j]
        data_3[i][j][cnt][2] <- img[i][j+m]
        data_3[i][j][cnt][3] <- img[i][j+n]
        cnt <- cnt + 1
      }
    }
    
  }
}

print (dim(data_3))
print ("finished")

