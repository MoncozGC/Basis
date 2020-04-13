"""
Author: MoncozGC
Data: 2020/4/12
"""

# 输入年份判断是不是闰年。

year = int(input('请输入年份: '))
# 使用\对代码进行折行
is_leap = year % 4 == 0 and year % 100 != 0 or \
          year % 400 == 0
print(is_leap)