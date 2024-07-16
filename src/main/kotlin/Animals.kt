package org.example

import org.bson.codecs.pojo.annotations.BsonDiscriminator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

// Base class
@BsonDiscriminator
@NoArg
open class Animal(
    @BsonId val id: ObjectId? = null,
    val name: String,
    val type: String
)

// Subclass
@NoArg
class Cat(
    id: ObjectId? = null,
    name: String,
    val favoriteToy: String
) : Animal(id, name, "Cat")

// Subclass
@NoArg
class Dog(
    id: ObjectId? = null,
    name: String,
    val favoritePark: String
) : Animal(id, name, "Dog")