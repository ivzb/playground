package advent_of_code

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class InputTest {

    @Test
    fun run2025() {
        mapOf(
            advent_of_code._2025.Task01 to (1102 to 6175),
            advent_of_code._2025.Task02 to (55916882972L to 76169125915L),
            advent_of_code._2025.Task03 to (17694L to 175659236361660L),
            advent_of_code._2025.Task04 to (1320 to 8354),
            advent_of_code._2025.Task05 to (694 to 352716206375547L),
            advent_of_code._2025.Task06 to (5316572080628L to 11299263623062L),
        ).forEach { task, (expectedPartA, expectedPartB) ->
            assertEquals(expectedPartA, task.partA())
            assertEquals(expectedPartB, task.partB())
        }
    }

    @Test
    fun run2024() {
        mapOf(
            advent_of_code._2024.Task01 to (2375403 to 23082277),
            advent_of_code._2024.Task02 to (472 to 520),
            advent_of_code._2024.Task03 to (188192787 to 113965544),
            advent_of_code._2024.Task04 to (2551 to 1985),
            advent_of_code._2024.Task05 to (6041 to 4884),
            advent_of_code._2024.Task06 to (4515 to 1309),
            advent_of_code._2024.Task07 to (5702958180383L to 92612386119138L),
            advent_of_code._2024.Task08 to (341 to 1134),
            advent_of_code._2024.Task09 to (6288707484810L to 6311837662089L),
            advent_of_code._2024.Task10 to (811 to 1794),
            advent_of_code._2024.Task11 to (235850L to 279903140844645L),
            advent_of_code._2024.Task12 to (1494342 to 893676),
            advent_of_code._2024.Task13 to (28059L to 102255878088512L),
            advent_of_code._2024.Task14 to (217328832 to 7412),
            advent_of_code._2024.Task15 to (1406392 to 1429013),
        ).forEach { task, (expectedPartA, expectedPartB) ->
            assertEquals(expectedPartA, task.partA())
            assertEquals(expectedPartB, task.partB())
        }
    }


    @Test
    fun run2023() {
        mapOf(
            advent_of_code._2023.Task01 to (56108 to 55652),
            advent_of_code._2023.Task02 to (2169 to 60948),
            advent_of_code._2023.Task03 to (537732 to 84883664),
            advent_of_code._2023.Task04 to (28750 to 10212704),
//            advent_of_code._2023.Task05 to (993500720L to 4917124L),
            advent_of_code._2023.Task06 to (1731600 to 40087680),
            advent_of_code._2023.Task07 to (251927063L to 255632664L),
            advent_of_code._2023.Task08 to (14257L to 16187743689077L),
            advent_of_code._2023.Task09 to (2075724761L to 1072L),
            advent_of_code._2023.Task10 to (6979 to -1),
            advent_of_code._2023.Task11 to (10231178L to 622120986954L),
            advent_of_code._2023.Task12 to (6871 to -1),
        ).forEach { task, (expectedPartA, expectedPartB) ->
            assertEquals(expectedPartA, task.partA())
            assertEquals(expectedPartB, task.partB())
        }
    }

    @Test
    fun run2022() {
        mapOf(
            advent_of_code._2022.Task01 to (66306 to 195292),
            advent_of_code._2022.Task02 to (13446 to 13509),
            advent_of_code._2022.Task03 to (7997 to 2545),
            advent_of_code._2022.Task04 to (466 to 865),
            advent_of_code._2022.Task05 to ("GRTSWNJHH" to "QLFQDBBHM"),
            advent_of_code._2022.Task06 to (1779 to 2635),
            advent_of_code._2022.Task07 to (2104783 to 5883165),
            advent_of_code._2022.Task08 to (1713 to 268464),
            advent_of_code._2022.Task09 to (6023 to 2533),
            advent_of_code._2022.Task10 to (15140 to "###..###....##..##..####..##...##..###..\n" +
                                      "#..#.#..#....#.#..#....#.#..#.#..#.#..#.\n" +
                                      "###..#..#....#.#..#...#..#....#..#.#..#.\n" +
                                      "#..#.###.....#.####..#...#.##.####.###..\n" +
                                      "#..#.#....#..#.#..#.#....#..#.#..#.#....\n" +
                                      "###..#.....##..#..#.####..###.#..#.#...."),
            advent_of_code._2022.Task11 to (56350L to 13954061248L),
            advent_of_code._2022.Task12 to (391 to 386),
            advent_of_code._2022.Task13 to (5625 to 23111),
            advent_of_code._2022.Task14 to (774 to 22499),
            advent_of_code._2022.Task15 to (4886370 to 11374534948438L),
            advent_of_code._2022.Task16 to (1873 to 2425),
            advent_of_code._2022.Task17 to (3227 to Unit),
            advent_of_code._2022.Task18 to (4482 to 2576),
            advent_of_code._2022.Task19 to (1356 to 27720),
            advent_of_code._2022.Task20 to (11123L to 4248669215955L),
            advent_of_code._2022.Task21 to (85616733059734L to 3560324848168L),
            advent_of_code._2022.Task22 to (186128 to 34426),
            advent_of_code._2022.Task23 to (3940 to 990),
            advent_of_code._2022.Task24 to (286 to 820),
            advent_of_code._2022.Task25 to ("2=01-0-2-0=-0==-1=01" to Unit),
        ).forEach { task, (expectedPartA, expectedPartB) ->
            assertEquals(expectedPartA, task.partA())
            assertEquals(expectedPartB, task.partB())
        }
    }

    @Test
    fun run2021() {
        mapOf(
            advent_of_code._2021.Task01 to (1832 to 1858),
            advent_of_code._2022.Task02 to (2147104 to 2044620088),
        ).forEach { task, (expectedPartA, expectedPartB) ->
            assertEquals(expectedPartA, task.partA())
            assertEquals(expectedPartB, task.partB())
        }
    }

}