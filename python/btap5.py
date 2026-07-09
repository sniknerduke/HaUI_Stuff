import os
os.system('cls')

def khoitao():
    n = int(input("Nhập số thiết bị: "))
    while n < 2:
        n = int(input("Nhập số thiết bị: "))
    
    ds = {
        input(f"\nMã thiết bị {i+1}: "):
        [
            input("Tên thiết bị: "),
            input("Loại thiết bị: "),
            int(input("Năm mua: ")),
            input("Phòng sử dụng: ")
        ]
        for i in range(n)
    }
    return ds

def tieude():
    print(f"|{'Mã TB':<15}|{'Tên TB':<15}|{'Loại':<15}|{'Năm mua':<15}|{'Phòng SD':<15}|{'Giá trị':<15}|")

def inDL(ds):
    for x,y in ds.items():
        print(f"|{x:<15}|{y[0]:<15}|{y[1]:<15}|{y[2]:<15}|{y[3]:<15}|{y[4] if len(y) >= 5 else 0:<15}|")
    print()

def themGT(ma,ds):
    if ma in ds:
        ds[ma].append(float(input("Giá trị: ")))
    return ds

def kiemtra(ds,ma):
    if ma in ds:
        return len(ds[ma]) >= 5
    return False

def tong(ds):
    return sum(y[4] for x,y in ds.items() if len(y) >= 5)

ds = khoitao()
tieude()
inDL(ds)

for x,y in ds.items():
    if not kiemtra(ds,x):
        print("Mã tb: ",x)
        themGT(x,ds)

tieude()
inDL(ds)

print(tong(ds))

