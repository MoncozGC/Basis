"""
输入三条边长，如果能构成三角形就计算周长和面积

Author: MoncozGC
Data: 2020/4/13
"""

x = float(input('x = '))
y = float(input('y = '))
z = float(input('z = '))

if x + y > z and y + z > x and y + z > x:
    print('周长: %.2f' % (x + y + z))
    p = (x + y + z) / 2
    area = (p * (p - x) * (p - y) * (p - z)) ** 0.5
    print('面积: %.2f' % (area))
else:
    print('不能构成三角形')
