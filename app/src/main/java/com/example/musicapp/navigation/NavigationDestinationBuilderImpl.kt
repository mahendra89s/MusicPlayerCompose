package com.example.musicapp.navigation

class NavigationDestinationBuilderImpl(
    private val route: String,
    private vararg val argsValues: List<String>
) : NavigationDestinationBuilder {

    override fun buildFullPath(): String {
        if (argsValues.isEmpty())
            return route
        return pathWithArgs()
    }

    private fun pathWithArgs(): String {
        return buildString {
            append(route)
            argsValues.forEach {
                append("/$it")
            }
        }
    }
}
