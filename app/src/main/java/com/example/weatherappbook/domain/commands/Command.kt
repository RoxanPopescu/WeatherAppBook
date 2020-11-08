package com.example.weatherappbook.domain.commands

interface Command<out T> {
    fun execute(): T
}