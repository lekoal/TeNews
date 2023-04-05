package com.private_projects.tenews.di.ferra

import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.ui.ferra.FerraNewsFragment
import com.private_projects.tenews.ui.ferra.FerraPagerAdapter
import com.private_projects.tenews.ui.ferra.FerraViewModel
import com.private_projects.tenews.utils.RssCommon
import org.koin.core.qualifier.named
import org.koin.dsl.module

val ferraKoinModule = module {
    single(named("rss_ferra")) {
        RssCommon(VkHelpData.FERRA_RSS)
    }
    scope<FerraNewsFragment> {
        scoped(named("ferra_adapter")) {
            FerraPagerAdapter()
        }
        scoped(named("ferra_view_model")) {
            FerraViewModel(get(named("rss_ferra")))
        }
    }
}