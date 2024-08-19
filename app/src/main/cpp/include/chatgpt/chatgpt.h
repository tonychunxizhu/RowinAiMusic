//
// Created by tony on 17/8/2024.
//

#ifndef RESTDEMO_CHATGPT_H
#define RESTDEMO_CHATGPT_H
#include <string>
#include "curl/curl.h"
#include <iostream>
#include "nlohmann/json.hpp"
#include <android/log.h>
#include <unistd.h>
#include "nlohmann/json.hpp"

std::string getCompletion(const std::string &);

std::string chatentry();


#endif //RESTDEMO_CHATGPT_H
