package org.gdg.frisbee.android.app;

import android.content.Context;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;

import org.gdg.frisbee.android.common.GdgActivity;
import org.gdg.frisbee.android.utils.PrefUtils;

public final class GoogleApiClientFactory {
    private GoogleApiClientFactory() {
    }

    public static GoogleApiClient createWith(Context context, boolean enableAutoManage) {
        return createBuilder(context, PrefUtils.isSignedIn(context), enableAutoManage).build();
    }

    public static GoogleApiClient createWith(GdgActivity gdgActivity) {
        return createWith(gdgActivity, true);
    }

    public static GoogleApiClient createWithApi(GdgActivity gdgActivity,
                                                Api<? extends Api.ApiOptions.NotRequiredOptions> api) {
        return createBuilder(gdgActivity, PrefUtils.isSignedIn(gdgActivity), true)
            .addApi(api)
            .build();
    }

    private static GoogleApiClient.Builder createBuilder(Context context, boolean withSignIn,
                                                         boolean enableAutoManage) {
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(context)
            .addApi(AppIndex.API);

        if (context instanceof GdgActivity && enableAutoManage) {
            GdgActivity gdgActviity = (GdgActivity) context;
            builder.enableAutoManage(gdgActviity, gdgActviity);
        }

        if (withSignIn) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PLUS_ME))
                .build();

            builder.addApi(Auth.GOOGLE_SIGN_IN_API, gso);
        }

        return builder;
    }

}
