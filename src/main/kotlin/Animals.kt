package org.example

import org.bson.codecs.pojo.annotations.BsonDiscriminator
import java.util.*

// Base class
@BsonDiscriminator
@NoArg
sealed class Animal(
    var id: UUID? = UUID.randomUUID(),
    var name: String,
    var type: String
)

// Subclass
class Cat(
    id: UUID? = UUID.randomUUID(),
    name: String,
    var favoriteToy: String
) : Animal(id, name, "Cat")

// Subclass
class Dog(
    id: UUID? = UUID.randomUUID(),
    name: String,
    var favoritePark: String
) : Animal(id, name, "Dog")