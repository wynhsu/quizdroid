package edu.washington.wynhsu.quizdroid

interface TopicRepository{
    fun add(t: Topic)
    fun update(t: Topic)
    fun remove(t: Topic)
//    fun query(s: Specification)
}