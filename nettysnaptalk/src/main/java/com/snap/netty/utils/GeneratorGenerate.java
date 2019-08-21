package com.snap.netty.utils;



import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GeneratorGenerate {
    public void generator() throws Exception {
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;

        File configFile = new File("nettysnaptalk/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,callback,warnings);
        myBatisGenerator.generate(null);
        for(String warning : warnings) {
            System.out.println(warning);
        }
    }

    public static void main(String[] args) {

        try {
            GeneratorGenerate generatorGenerate = new GeneratorGenerate();
            generatorGenerate.generator();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
