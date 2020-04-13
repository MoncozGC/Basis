"""
英制单位英寸与公制单位厘米互换

Author: MoncozGC
Data: 2020/4/13
"""

value = float(input("请输入尺寸: "))
unit = input("请输入单位: ").lower()

if unit == "in" or unit == "尺寸":
    print('%f英寸 = %f厘米' % (value, value * 2.54))
elif unit == "cm" or unit == "厘米":
    print('%f厘米 = %f英寸' % (value, value / 2.54))
else:
    print("请输入有效的单位")
