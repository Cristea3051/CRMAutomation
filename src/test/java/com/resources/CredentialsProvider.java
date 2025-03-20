package com.resources;

import org.testng.annotations.DataProvider;

public class CredentialsProvider {


    @DataProvider(name = "GlobalCred")
    public Object[][] providerCreds() {
        return new Object[][] {
                { "victor.cristea@vebo.io", "j8L3pc5hJ20Sjn10Lp!" },
        };
    }

    @DataProvider(name = "credentials")
    public Object[][] providerDataSet() {
        return new Object[][] {
                { "victor.cristea@vebo.io", "j8L3pc5hJ20Sjn10Lp!" },
                { "andreic@vebo.io", "j8L3pc5hJ20Sjn10Lp!" },
                { "dorin.m@vebo.io", "j8L3pc5hJ20Sjn10Lp!" },
                { "marcel.g@vebo.io", "j8L3pc5hJ20Sjn10Lp!" },
        };
    }

    @DataProvider(name = "FarmerGlobalCredentials")
    public Object[][] FarmerGlobalProviderDataSet() {
        return new Object[][] {
                { "victor.cristea@vebo.io", "j8L3pc5hJ20Sjn10Lp!" },
                { "chriss.c@vebo.io", "j8L3pc5hJ20Sjn10Lp!" },
                { "marcel.g@vebo.io", "j8L3pc5hJ20Sjn10Lp!" }
        };
    }

    @DataProvider(name = "MediaBuyerGlobalCredentials")
    public Object[][] MediaBuyerGlobalProviderDataSet() {
        return new Object[][] {
                { "victor.cristea@vebo.io", "j8L3pc5hJ20Sjn10Lp!" },
                { "dorin.m@vebo.io", "j8L3pc5hJ20Sjn10Lp!" },
                { "edward.s@vebo.io", "j8L3pc5hJ20Sjn10Lp!" },
                { "andreic@vebo.io", "j8L3pc5hJ20Sjn10Lp!" },

        };
    }


    @DataProvider(name = "ClientCredentials")
    public Object[][] ClientCredentialsProviderDataSet() {
        return new Object[][] {
                { "searchmrclient@vebo.io" , "j8L3pc5hJ20Sjn10Lp!"},
                { "fbclient@vebo.io" , "j8L3pc5hJ20Sjn10Lp!"},
                { "reportnew@vebo.io" , "j8L3pc5hJ20Sjn10Lp!"},
                { "nlmrclient@vebo.io" , "j8L3pc5hJ20Sjn10Lp!"},
                { "itmrclient@vebo.io" , "j8L3pc5hJ20Sjn10Lp!"},
                { "grmrclient@vebo.io" , "j8L3pc5hJ20Sjn10Lp!"},
                { "demrclient@vebo.io" , "j8L3pc5hJ20Sjn10Lp!"},
                { "chmrclient@vebo.io" , "j8L3pc5hJ20Sjn10Lp!"},
                { "atmrclient@vebo.io" , "j8L3pc5hJ20Sjn10Lp!"},
                { "s2bclient@vebo.io" , "j8L3pc5hJ20Sjn10Lp!"},
                { "demandgen@vebo.io" , "j8L3pc5hJ20Sjn10Lp!"},
                { "bodmrclient@vebo.io" , "j8L3pc5hJ20Sjn10Lp!"},
                { "dlpmrclient@vebo.io" , "j8L3pc5hJ20Sjn10Lp!"},
                { "kngmr@vebo.io" , "j8L3pc5hJ20Sjn10Lp!"},
                { "spmrclient@vebo.io" , "j8L3pc5hJ20Sjn10Lp!"},
                { "bdgmr-client@vebo.io" , "j8L3pc5hJ20Sjn10Lp!"},
                { "fpmr-client@vebo.io" , "j8L3pc5hJ20Sjn10Lp!"},
                { "boomr-client@vebo.io" , "j8L3pc5hJ20Sjn10Lp!"},
                { "bbmr-client@vebo.io" , "j8L3pc5hJ20Sjn10Lp!"}, 

        };
    }
}