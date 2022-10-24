package ca.unb.mobiledev.gittogetherui.model

import java.util.UUID

/**
 * This makes use of the data class pattern (https://kotlinlang.org/docs/data-classes.html)
 * NOTES:
 *  Each value has a getter and setter
 */
data class Project(var title: String? = null,
                   var description: String? = null,
                   var tags: String? = null,
                   var id: UUID? = null)
