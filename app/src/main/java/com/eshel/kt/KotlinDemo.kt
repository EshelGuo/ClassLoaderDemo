package com.eshel.kt
//与 Java 不同, 与 C/C++ 类似, kotlin 可以在 class 外声明变量, 定义函数
import kotlin.io

//kotlin 方法声明： fun 方法名 (参数名: 参数类型, 参数名: 参数类型): 返回值(没有返回值可省略 ': 返回值')
fun main(args: Array<String>){
    //main 方法
}

fun test1(vararg args: Int){
    for (arg in args) {
        print(arg)
    }
}