package com.wardrobes.porenut.domain

import min3d.core.Object3dContainer
import min3d.parser.ParseObjectData
import min3d.parser.ParseObjectFace
import min3d.vos.Number3d
import java.io.InputStream
import java.lang.Float.parseFloat
import java.lang.Short.parseShort
import java.util.*

private const val KEY_VERTEX = "v"
private const val KEY_FACE = "f"
private const val KEY_NORMAL = "vn"
private const val KEY_OBJECT = "o"

object CustomObjParser {

    fun parse(file: InputStream): Object3dContainer {
        val vertices = mutableListOf<Number3d>()
        val normals = mutableListOf<Number3d>()
        val parseObjects = mutableListOf<ParseObjectData>()

        file.bufferedReader().useLines { lines ->
            lines.iterator().forEach {
                val parts = StringTokenizer(it, " ")
                val numTokens = parts.countTokens()
                if (numTokens != 0) {
                    when (parts.nextToken()) {
                        KEY_VERTEX -> vertices.add(parts.toNumber3d())
                        KEY_FACE -> parseObjects.last().addFace(ObjFace(it, numTokens - 1))
                        KEY_NORMAL -> normals.add(parts.toNumber3d())
                        KEY_OBJECT -> parseObjects.add(ParseObjectData(vertices, normals))
                    }
                }
            }
        }

        return Object3dContainer(0, 0).apply {
            parseObjects.forEach { addChild(it.parsedObject) }
        }
    }

    private fun StringTokenizer.toNumber3d() = Number3d().apply {
        x = parseFloat(nextToken())
        y = parseFloat(nextToken())
        z = parseFloat(nextToken())
    }

    private class ObjFace constructor(line: String, faceLength: Int) : ParseObjectFace() {

        init {
            this.faceLength = faceLength
            var line = line
            val emptyVt = line.indexOf("//") > -1
            if (emptyVt) line = line.replace("//", "/")
            val parts = StringTokenizer(line)
            parts.nextToken()
            var subParts = StringTokenizer(parts.nextToken(), "/")
            val partLength = subParts.countTokens()
            hasuv = partLength >= 2 && !emptyVt
            hasn = partLength == 3 || partLength == 2 && emptyVt

            v = IntArray(faceLength)
            if (hasuv) uv = IntArray(faceLength)
            if (hasn) n = IntArray(faceLength)

            for (i in 1 until faceLength + 1) {
                if (i > 1) subParts = StringTokenizer(parts.nextToken(), "/")

                val index = i - 1
                v[index] = (parseShort(subParts.nextToken()) - 1).toShort().toInt()
                if (hasuv) uv[index] = (parseShort(subParts.nextToken()) - 1).toShort().toInt()
                if (hasn) n[index] = (parseShort(subParts.nextToken()) - 1).toShort().toInt()
            }
        }
    }
}