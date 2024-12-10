package com.resources;

import org.testng.annotations.DataProvider;

public class CredentialsProvider {

    @DataProvider(name = "credentials")
    public Object[][] providerDataSet() {
        return new Object[][] {
                { "victor.cristea@vebo.io", "j8L3pc5hJ20Sjn10Lp!" },
                { "andreic@vebo.io", "j8L3pc5hJ20Sjn10Lp!" },
                { "dorin.m@vebo.io", "j8L3pc5hJ20Sjn10Lp!" },
                { "edward.s@vebo.io", "j8L3pc5hJ20Sjn10Lp!" },
                { "marcel.g@vebo.io", "j8L3pc5hJ20Sjn10Lp!" },
        };
    }

    @DataProvider(name = "FarmerGlobalCredentials")
    public Object[][] FarmerGlobalProviderDataSet() {
        // Filtrare pentru utilizatorii selectați
        return new Object[][] {
                { "victor.cristea@vebo.io", "j8L3pc5hJ20Sjn10Lp!" },
                { "cristian.ciubuc@vebo.io", "j8L3pc5hJ20Sjn10Lp!" },
                { "marcel.g@vebo.io", "j8L3pc5hJ20Sjn10Lp!" }
        };
    }

    @DataProvider(name = "MediaBuyerGlobalCredentials")
    public Object[][] MediaBuyerGlobalProviderDataSet() {
        // Filtrare pentru utilizatorii selectați
        return new Object[][] {
                { "victor.cristea@vebo.io", "j8L3pc5hJ20Sjn10Lp!" },
                // { "dorin.m@vebo.io", "Morris22##" },
                // { "edward.s@vebo.io", "Morris22##" },
                // { "andreic@vebo.io", "Morris22##" },

        };
    }
}