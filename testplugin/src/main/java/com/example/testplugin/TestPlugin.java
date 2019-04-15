package com.example.testplugin;

import com.android.build.api.dsl.extension.AppExtension;
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.Collections;

public class TestPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        System.out.println("TestPlugin:");
        BaseAppModuleExtension appExtension = (BaseAppModuleExtension) project.getProperties().get("android");
        appExtension.registerTransform(new CustomTransform());
    }
}
