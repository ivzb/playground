import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class InputTest {

    @Test
    fun run() {
        mapOf(
            _2021.Task01 to (1832 to 1858),
            _2022.Task02 to (2147104 to 2044620088),

            _2022.Task01 to (66306 to 195292),
            _2022.Task02 to (13446 to 13509),
            _2022.Task03 to (7997 to 2545),
            _2022.Task04 to (466 to 865),
            _2022.Task05 to ("GRTSWNJHH" to "QLFQDBBHM"),
            _2022.Task06 to (1779 to 2635),
            _2022.Task07 to (2104783 to 5883165),
            _2022.Task08 to (1713 to 268464),
            _2022.Task09 to (6023 to 2533),
            _2022.Task10 to (15140 to "###..###....##..##..####..##...##..###..\n" +
                                      "#..#.#..#....#.#..#....#.#..#.#..#.#..#.\n" +
                                      "###..#..#....#.#..#...#..#....#..#.#..#.\n" +
                                      "#..#.###.....#.####..#...#.##.####.###..\n" +
                                      "#..#.#....#..#.#..#.#....#..#.#..#.#....\n" +
                                      "###..#.....##..#..#.####..###.#..#.#...."),
            _2022.Task11 to (56350L to 13954061248L),
            _2022.Task12 to (391 to 386),
            _2022.Task13 to (5625 to 23111),
            _2022.Task14 to (774 to 22499),
            _2022.Task15 to (4886370 to 11374534948438L),
            _2022.Task16 to (1873 to 2425),
            _2022.Task17 to (3227 to Unit),
            _2022.Task18 to (4482 to 2576),
            _2022.Task19 to (1356 to 27720),
            _2022.Task20 to (11123L to 4248669215955L),
            _2022.Task21 to (85616733059734L to 3560324848168L),
        ).forEach { task, (expectedPartA, expectedPartB) ->
            assertEquals(expectedPartA, task.partA())
            assertEquals(expectedPartB, task.partB())
        }
    }

}