package com.example.weatherappbook.domain.commands

interface Command<out T> {
    suspend fun execute(): T
}