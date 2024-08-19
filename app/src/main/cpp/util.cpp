//
// Created by tony on 17/8/2024.
//
#include <string>
#include <iostream>
#include <jni.h>
using std::string;
//static
/*size_t WriteCallback(void *contents, size_t size, size_t nmemb, void *userp)
{
    ((std::string*)userp)->append((char*)contents, size * nmemb);
    return size * nmemb;
}
*/
size_t WriteCallback(void *ptr, size_t size, size_t nmemb, void *stream) {
    size_t realsize = size * nmemb;
    string *response = (string *)stream;

    response->append((char *)ptr, realsize);
    return realsize;
}

std::string jstring_to_string(JNIEnv* env, jstring jstr) {
    if (!jstr) {
        return "";
    }

    const char* chars = env->GetStringUTFChars(jstr, nullptr);
    if (!chars) {
        return "";
    }

    std::string str(chars);
    env->ReleaseStringUTFChars(jstr, chars);

    return str;
}