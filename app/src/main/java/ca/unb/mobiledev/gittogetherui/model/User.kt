package ca.unb.mobiledev.gittogetherui.model

import java.io.Serializable
import java.util.UUID

/**
 * This makes use of the data class pattern (https://kotlinlang.org/docs/data-classes.html)
 * NOTES:
 *  Each value has a getter and setter
 */
data class User(var name: String? = null,
                var bio: String? = null,
                var location: String? = null,
                var email: String? = null,
                var bearer: String? = null,
                var id: UUID? = null,) : Serializable