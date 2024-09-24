package com.resources;

import org.testng.annotations.DataProvider;

public class CredentialsProvider {

    @DataProvider(name = "credentials")
    public Object[][] providerDataSet() {
        return new Object[][] {
                { "victor.cristea@vebo.io", "Morris22##" },
                { "andreic@vebo.io", "Morris22##" },
                { "dorin.m@vebo.io", "Morris22##" },
                { "edward.s@vebo.io", "Morris22##" },
                { "marcel.g@vebo.io", "Morris22##" },
        };
    }

    @DataProvider(name = "FarmerGlobalCredentials")
    public Object[][] FarmerGlobalProviderDataSet() {
        // Filtrare pentru utilizatorii selectați
        return new Object[][] {
                { "victor.cristea@vebo.io", "Morris22##" },
                { "cristian.ciubuc@vebo.io", "Morris22##" },
                { "marcel.g@vebo.io", "Morris22##" }
        };
    }

    @DataProvider(name = "MediaBuyerGlobalCredentials")
    public Object[][] MediaBuyerGlobalProviderDataSet() {
        // Filtrare pentru utilizatorii selectați
        return new Object[][] {
                { "victor.cristea@vebo.io", "Morris22##" },
                { "dorin.m@vebo.io", "Morris22##" },
                { "edward.s@vebo.io", "Morris22##" },
                { "andreic@vebo.io", "Morris22##" },

        };
    }
}