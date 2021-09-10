package io.github.galaxyvn.gcontrolpanel.api

import taboolib.module.configuration.Config
import taboolib.module.configuration.SecuredFile

object GControlPanelFiles {

    @Config("settings.yml", migrate = true)
    lateinit var settings: SecuredFile
        private set
}