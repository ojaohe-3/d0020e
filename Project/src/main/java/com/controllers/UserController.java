package com.controllers;

import Data.*;
import SpecificInterfaces.UserInterface;

public class FilterController extends UserInterface {
    @Override
    public Course getCourseByCode(String courseCode, CourseDate courseDate) {
        return super.getCourseByCode(courseCode, courseDate);
    }

    @Override
    public ProgramInformation[] searchProgram(CourseProgram.ProgramLabels tag, String searchKey) {
        return super.searchProgram(tag, searchKey);
    }

    @Override
    public KC[] searchKcTopic(String topic) {
        return super.searchKcTopic(topic);
    }

    @Override
    public KC[] searchKC(KC.KCLabel tag, String searchKey) {
        return super.searchKC(tag, searchKey);
    }

    @Override
    public CourseInformation[] getCourseByTag(Course.CourseLabels tag, String searchTerm) {
        return super.getCourseByTag(tag, searchTerm);
    }
}
