package ca.unb.mobiledev.gittogetherui.model

import android.location.Location
import java.io.Serializable
import java.util.UUID

/**
 * This makes use of the data class pattern (https://kotlinlang.org/docs/data-classes.html)
 * NOTES:
 *  Each value has a getter and setter
 */
data class Project(var name: String? = null,
                   var description: String? = null,
                   var location: String? = null,
                   var tags: String? = null,
                   var link: String? = null,
                   var id: UUID? = null) : Serializable
