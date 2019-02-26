# Sets the minimum version of CMake required to build the native
# library. You should either keep the default value or only pass a
# value of 3.4.0 or lower.
cmake_minimum_required(VERSION 3.4.1)

#是否查看更多cmake编译时候的信息
set(CMAKE_VERBOSE_MAKEFILE ON)

PROJECT(lib_face)

#MESSAGE输出的信息请在androidstudio的Gradle Console中查看
MESSAGE(当前CMakeLists.txt所在目录： ${CMAKE_CURRENT_SOURCE_DIR} )


#添加子工程的目录
ADD_SUBDIRECTORY(src/main/cpp/GSFaceLib)


