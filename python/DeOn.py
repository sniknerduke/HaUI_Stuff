import os
os.system('cls')

def khoitao():
    n = int(input("Nhập số lượng sách: ")) 
    while n < 3:
        n = int(input("Nhập số lượng sách: "))
    
    ds = {
        input(f"\nMã sách {i+1}: "):
        {
            'ten': input("Tên sách: "),
            'the_loai': input("Thể loại: "),
            'namxb': int(input("Năm xuất bản: ")),
            'gia': float(input("Giá: ")),
            'soluong': int(input("Số lượng: "))
            ,'soluotmuon':int(input("số lượt mượn: "))
            ,'danhgia': int(input("Đánh giá: "))
        }
        for i in range(n)
    }
    return ds

def inDL(ds):
    print(f"|{'Mã sách':<15}|{'Tên sách':<15}|{'Thể loại':<15}|{'Năm Xuất Bản':<15}|{'Giá':<15}|{'Số lượng':<15}|{'Số lượt mượn':<15}|{'Đánh giá':<15}|")
    if type(ds) is dict:
        for x,y in ds.items():
            print(f"|{x:<15}|{y['ten']:<15}|{y['the_loai']:<15}|{y['namxb']:<15}|{y['gia']:<15}|{y['soluong']:<15}|{y['soluotmuon']:<15}|{y['danhgia']:<15}|")
    else:
        for x,y in ds:
            print(f"|{x:<15}|{y['ten']:<15}|{y['the_loai']:<15}|{y['namxb']:<15}|{y['gia']:<15}|{y['soluong']:<15}|{y['soluotmuon']:<15}|{y['danhgia']:<15}|")
    print()

def TBC(ds):
    return sum(y['soluotmuon'] for x,y in ds.items()) / len(ds)

def banchay(ds):
    return {x:y for x,y in ds.items() if y['soluotmuon'] > TBC(ds)}

def sapxep(ds):
    return sorted(banchay(ds).items(), key=lambda x:x[1]['soluotmuon'],reverse= True)

def xoads(ds,nam):
    a = [x for x,y in ds.items() if  y['namxb'] < nam and y['soluong'] == 0]
    for i in a:
        del ds[i]
    return len(a),ds

def ghifile(ds):
    with open('\Code\Py\CK\Lan1\DeOn.txt','w',encoding = 'utf-8') as f:
        f.write(f'|Mã sách        |Tên sách       |Thể loại       |Năm Xuất Bản   |Giá            |Số lượng       |Số lượt mượn   |Đánh giá       |\n')
        for x,y in ds.items():
            f.write(f"|{x:<15}|{y['ten']:<15}|{y['the_loai']:<15}|{y['namxb']:<15}|{y['gia']:<15}|{y['soluong']:<15}|{y['soluotmuon']:<15}|{y['danhgia']:<15}|\n")

def suaTL(ds):
    a = [x for x,y in ds.items() if y['the_loai'] == 'tết']
    for i in a:
        ds[i]['soluong'] = 100
    return ds 

def themchiphi(ds):
    a = [x for x,y in ds.items() if 'chiphi' not in y]

    for x in a:
        ds[x]['chiphi'] = float(input("Chi phí: "))
    return ds

# ds = khoitao()
ds = {'1': {'ten': '1', 'the_loai': '1', 'namxb': 2000, 'gia': 1.0, 'soluong': 1, 'soluotmuon': 15, 'danhgia': 1}, 
      '2': {'ten': '2', 'the_loai': 'tết', 'namxb': 2016, 'gia': 2.0, 'soluong': 0, 'soluotmuon': 24, 'danhgia': 2}, 
      '3': {'ten': '3', 'the_loai': '3', 'namxb': 2020, 'gia': 3.0, 'soluong': 3, 'soluotmuon': 36, 'danhgia': 3}}
ds |= {
    '4': {'ten': '4', 'the_loai': '4', 'namxb': 2001, 'gia': 4.0, 'soluong': 0, 'soluotmuon': 5, 'danhgia': 4},
    '5': {'ten': '5', 'the_loai': 'tết', 'namxb': 1999, 'gia': 5.0, 'soluong': 26, 'soluotmuon': 20, 'danhgia': 5}
}

inDL(ds)


inDL(sapxep(ds))

sl,newd = xoads(ds,2018)
print("Số lượng sách đã xóa là: ",sl)
inDL(newd)

inDL(suaTL(ds))

a = themchiphi(ds)
print(a)
# ghifile(ds)


    