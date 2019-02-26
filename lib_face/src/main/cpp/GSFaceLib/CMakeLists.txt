# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

#jniLibs路径
set(JNILIBS_SO_PATH ${CMAKE_CURRENT_SOURCE_DIR}/../../jniLibs)


#添加头文件
# Specifies a path to native header files.
include_directories(${CMAKE_CURRENT_SOURCE_DIR}/)
include_directories(${CMAKE_CURRENT_SOURCE_DIR}/include/)
include_directories(${CMAKE_CURRENT_SOURCE_DIR}/util/)




# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

# add_library( # Sets the name of the library.
#              GSVideoStack
# 
#              # Sets the library as a shared library.
#              SHARED
# 
#              # Provides a relative path to your source file(s).
#              #添加关联的源文件
#              GSVideoStack-jni.cpp 
#              CUtil.cpp
#              GSDevice-jni.cpp
#              GSUser-jni.cpp
#              GSAlarm-jni.cpp
#              GSTalk-jni.cpp
#              GSRecord-jni.cpp
#              GSMap-jni.cpp
#              GSVideo-jni.cpp
#              GSPtzControl-jni.cpp
#              )


## 下面这种写法自动遍历 源文件，不用每次变动都去修改。
# 定义源文件目录
get_filename_component(CPP_SRC_DIR ${CMAKE_CURRENT_SOURCE_DIR}/../  ABSOLUTE)
# 定义源文件目录下的源文件
file(GLOB_RECURSE cpp_sources ${CPP_SRC_DIR}/*.*)
add_library( # Sets the name of the library.                              
             GSFaceRecog                                                 
                                                                          
             # Sets the library as a shared library.                      
             SHARED                                                       
                                                                          
             # Provides a relative path to your source file(s).           
             #添加关联的源文件                                                    
               ${cpp_sources}       
            GSFaceRecog.cpp
             )                                                            

#添加其他预编译好的so或者a文件（静态）
#Add other prebuilt libraries

 add_library(GoNcFaceRegSDK-lib  STATIC IMPORTED )
 set_target_properties(GoNcFaceRegSDK-lib PROPERTIES IMPORTED_LOCATION
                       ${JNILIBS_SO_PATH}/${ANDROID_ABI}/libGoNcFaceRegSDK.so )
                        
 add_library(GoNcMtcnnSDK-lib  STATIC IMPORTED )
 set_target_properties(GoNcMtcnnSDK-lib PROPERTIES IMPORTED_LOCATION
                       ${JNILIBS_SO_PATH}/${ANDROID_ABI}/libGoNcMtcnnSDK.so )
 
 add_library(GoNcnnSDK-lib  STATIC IMPORTED )
 set_target_properties(GoNcnnSDK-lib PROPERTIES IMPORTED_LOCATION
                       ${JNILIBS_SO_PATH}/${ANDROID_ABI}/libGoNcnnSDK.so )

 add_library(ncnn-lib  STATIC IMPORTED )
 set_target_properties(ncnn-lib PROPERTIES IMPORTED_LOCATION
                       ${JNILIBS_SO_PATH}/${ANDROID_ABI}/libncnn.a )

add_library(opencv_java-lib  STATIC IMPORTED )
set_target_properties(opencv_java-lib PROPERTIES IMPORTED_LOCATION
        ${JNILIBS_SO_PATH}/${ANDROID_ABI}/libopencv_java.so )


# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       GSFaceRecog
                       GoNcFaceRegSDK-lib
                       GoNcMtcnnSDK-lib
                       GoNcnnSDK-lib
                       ncnn-lib
                       opencv_java-lib
                       
                       
                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )
                       
#指定so库生成位置（实际上是复制一份到该目录下）           
set_target_properties(GSFaceRecog
                      PROPERTIES
                      LIBRARY_OUTPUT_DIRECTORY
                      "${JNILIBS_SO_PATH}/${ANDROID_ABI}")