package com.hristodaskalov.mobileplanprovider.utils;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class ObjectMapper {


    public static <SourceType, TargetType> TargetType convertObject(SourceType object, Class<TargetType> targetClass) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(object, targetClass);
    }

    public static <SourceType, TargetType> List<TargetType> convertList(List<SourceType> list, Class<TargetType> targetClass) {
        List<TargetType> targetList = new ArrayList<>();
        list.forEach(object -> targetList.add(convertObject(object, targetClass)));
        return targetList;
    }
}
