library(png)

cat("started")

img <- readPNG(native=TRUE, "/home/dodler/Documents/hist[R]/srw/new/img/source.png")
d <- dim(img)
print("image loaded")

height = d[1]
width = d[2]

h <- hist(img)

print (paste("width:", width))
print (paste("height:", height))

#function returns image intensity at points i,j
image_intensity <- function (i,j){
  return (img[i,j])
}

#this function show if image has intensity levels i and j
# at points m1,m2
# and m1 + d_m1, m2+d_m2
indicator <- function(i,j, m1, m2, d_m1, d_m2){
  if (m1+d_m1 > height) return (0)
  if (m2 + d_m2 > width) return (0)
  
  return (img[m1,m2] == i && img[m1+d_m1, m2+d_m2]==j)
  #return (0)
}

sum_indicator <- function(m1,m2){
  i <- img[m1,m2]
  
  j_indexes <- expand.grid(1:height, 1:width)
  
  d_m1 <- unlist(j_indexes['Var1'])
  d_m2 <- unlist(j_indexes['Var2'])

  j <- mapply(image_intensity, d_m1, d_m2)
  
  i <- rep(i, width*height)
  
  #print("lengths")
  #print (length(i))
  #print (length(j))
  #print (length(m1))
  #print (length(m2))
  #print (length(d_m1))
  #print (length(d_m2))
  
  return (sum(mapply(indicator, i,j,m1,m2,d_m1, d_m2)))
}

pic_coords <- expand.grid(1:height, 1:width)

print("running mapply to sum_indicator")
data_3d <- mapply(FUN=sum_indicator, unlist(pic_coords['Var1']), unlist(pic_coords['Var2']))
hist(data_3d)
print ("finished")

