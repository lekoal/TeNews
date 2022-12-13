package com.private_projects.tenews.di.ixbt

import com.private_projects.tenews.data.ixbt.VkIxbtApiHelperImpl
import com.private_projects.tenews.domain.ixbt.VkIxbtApi
import com.private_projects.tenews.domain.ixbt.VkIxbtApiHelper
import com.private_projects.tenews.ui.ixbt.IxbtNewsFragment
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val ixbtKoinModule = module {
    single(named("ixbt_api")) {
        get<Retrofit>(named("retrofit")).create(VkIxbtApi::class.java)
    }
    single<VkIxbtApiHelper>(named("ixbt_api_helper")) {
        VkIxbtApiHelperImpl(get(named("ixbt_api")))
    }

    scope<IxbtNewsFragment> {

    }
}