"""
Author: MoncozGC
Data: 2020/4/12
"""

# 输入圆的半径计算计算周长和面积。

radius = float(input("请输入圆的半径: "))
perimeter = 2 * 3.1416 * radius
area = radius * radius * 3.14
print("圆的面积是%0.2f, 圆的周长是%0.2f" % (area, perimeter))
