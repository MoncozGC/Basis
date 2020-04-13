"""
百分制成绩转换为等级制成绩
90-100 A+
80-90 A
70-80 B
60-70 C
0-60 D

Author: MoncozGC
Data: 2020/4/13
"""

score = float(input("请输入有效成绩: "))

if score >= 90 and score <= 100:
    grade = "A+"
elif score >= 80 and score < 90:
    grade = "A"
elif score >= 70 and score < 80:
    grade = "B"
elif score >= 60 and score < 70:
    grade = "C"
elif score >= 0 and score < 60:
    grade = "D"
else:
    print("请输入正确的成绩")

print(grade)
