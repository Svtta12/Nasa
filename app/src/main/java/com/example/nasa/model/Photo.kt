package com.example.nasa.model


data class Photo(
    val camera: Camera,
    val earth_date: String,
    val id: Int,
    val img_src: String,
    val rover: Rover,
    val sol: Int
)


data class Camera(
    val full_name: String,
    val id: Int,
    val name: String,
    val rover_id: Int
)