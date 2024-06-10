package com.resources;

import org.testng.annotations.DataProvider;

public class CredentialsProvider {

    @DataProvider(name = "credentials")
    public Object[][] providerDataSet() {
        return new Object[][] {
                { "victor.cristea@vebo.io", "j8L3pc5hJ20Sjn10Lp!" },
                { "andreic@vebo.io", "j8L3pc5hJ20Sjn10Lp!" },
                { "dorin.m@vebo.io", "j8L3pc5hJ20Sjn10Lp!" },
            
        };
    }
}