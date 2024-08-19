//
// Created by tony on 17/8/2024.
//

#ifndef RESTDEMO_UTIL_H
#define RESTDEMO_UTIL_H
#include <jni.h>
#include <string>

#include <iostream>

#include <android/log.h>
#include <unistd.h>


#define TAG "RESTDEBUG" // 这个是自定义的LOG的标识
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__) // 定义LOGF类型

//static
size_t WriteCallback(void *contents, size_t size, size_t nmemb, void *userp);
std::string jstring_to_string(JNIEnv* env, jstring jstr);

#endif //RESTDEMO_UTIL_H
