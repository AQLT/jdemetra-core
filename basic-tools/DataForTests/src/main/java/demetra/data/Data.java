/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demetra.data;

import demetra.timeseries.simplets.TsData;
import demetra.timeseries.simplets.TsFrequency;
import demetra.timeseries.simplets.TsPeriod;

/**
 *
 * @author Jean Palate <jean.palate@nbb.be>
 */
public class Data {

    public static final double[] EXPORTS = {
        9568.3, 9920.3, 11353.5, 9247.5, 10114.2, 10763.1, 8456.1, 8071.6, 10328, 10551.4, 10186.1, 8821.6,
        9841.3, 10233.6, 10794.6, 10289.3, 10513.4, 10607.6, 9707.4, 8103.5, 10982.6, 11836.9, 10517.5, 9810.5,
        10374.8, 10855.3, 11671.3, 11901.2, 10846.4, 11917.5, 11362.8, 9314.5, 12605.9, 12815.1, 11254.5, 11111.8,
        11282.9, 11554.5, 12935.6, 12146.3, 11615.3, 13214.8, 11735.5, 9522.3, 12694.8, 12317.6, 11450, 11380.9,
        10604.6, 10972.2, 13331.5, 11733.1, 11284.7, 13295.8, 11881.4, 10374.2, 13828, 13490.5, 13092.2, 13184.4,
        12398.4, 13882.3, 15861.5, 13286.1, 15634.9, 14211, 13646.8, 12224.6, 15916.4, 16535.9, 15796, 14418.6,
        15044.5, 14944.2, 16754.8, 14254, 15454.9, 15644.8, 14568.3, 12520.2, 14803, 15873.2, 14755.3, 12875.1,
        14291.1, 14205.3, 15859.4, 15258.9, 15498.6, 15106.5, 15023.6, 12083, 15761.3, 16943, 15070.3, 13659.6,
        14768.9, 14725.1, 15998.1, 15370.6, 14956.9, 15469.7, 15101.8, 11703.7, 16283.6, 16726.5, 14968.9, 14861,
        14583.3, 15305.8, 17903.9, 16379.4, 15420.3, 17870.5, 15912.8, 13866.5, 17823.2, 17872, 17420.4, 16704.4,
        15991.5, 16583.6, 19123.4, 17838.8, 17335.3, 19026.9, 16428.6, 15337.4, 19379.8, 18070.5, 19563, 18190.6,
        17658, 18437.9, 21510.4, 17111, 19732.7, 20221.8
    };
    public static final double[] PROD = {
        59.2, 58.3, 63.4, 59.7, 58.9, 62.7, 47.6, 58.6, 64.4, 66.4, 64.2, 62.2, 61.7, 62.2, 65.5, 64.6, 64.6, 62.2, 53.2, 62.5, 68.5, 73.5, 67.1, 68.6,
        69.1, 65.5, 72.7, 73, 70.3, 73.5, 61.5, 67.6, 77.7, 81.7, 73.5, 75.4, 70.6, 70.8, 76.9, 77.7, 71.1, 77.3, 63.1, 70.8, 80.5, 82.7, 75.8, 79.3,
        72.3, 74, 82.7, 79.1, 74.4, 79.5, 61.9, 73.5, 83.1, 82.9, 78, 80.4, 77.7, 79, 88.1, 79.5, 80.9, 85.7, 61.2, 78.7, 87.6, 91.5, 88.5, 86.6,
        86.8, 84.7, 94.1, 86.9, 90.2, 86.1, 68.8, 86.9, 90.7, 99.6, 94.9, 88.2, 95.2, 91.9, 97.5, 96.4, 95.2, 91.8, 74.7, 86.7, 96.2, 100.6, 89.7, 85.7,
        88.5, 83.8, 86.3, 86.7, 79, 84.2, 64.6, 72.6, 88.2, 91.1, 84, 85.8, 86.1, 88, 97.6, 95.3, 89.1, 93.5, 69.4, 86, 99.1, 97.3, 92.9, 92.7,
        90.2, 89.7, 102.3, 92, 89.1, 95.2, 67, 88.1, 95.6, 94.2, 93, 92.2, 91.5, 88.9, 99.1, 93.6, 91.5, 94.6, 67.6, 89.8, 99.3, 103.7, 100.3, 94.8,
        92.2, 93.8, 103.5, 98.8, 99.2, 99.5, 75.6, 96, 102.1, 109.3, 103.3, 96.3, 104.5, 102.8, 105.8, 102.3, 93.7, 99, 73, 87.9, 100.1, 103.8, 90.9, 89.1,
        91.6, 92.5, 100.3, 97.5, 90.4, 96.4, 70.8, 86.7, 102.5, 103.7, 96.8, 93.7, 93.4, 92.5, 99.9, 99.6, 91.5, 99.7, 70.6, 88.1, 102, 101.1, 94, 92.3,
        94.4, 93, 103.9, 96.1, 94.3, 102.2, 70, 93.5, 102.3, 102.5, 101.4, 94.5, 100.5, 100, 105.1, 96.3, 102.1, 97.8, 75.1, 94.3, 102, 110.4, 102.8, 92.9,
        99.4, 97.2, 105.5, 102.6, 99.7, 101, 79.6, 93.5, 107.7, 114, 104.5, 95.4, 104.1, 100.6, 104.6, 109, 95.7, 104.4, 82.5, 93.5, 109.6, 113.4, 100.6, 97.8,
        101.2, 101.7, 110.8, 108.7, 101.8, 107.2, 83, 97.5, 114.3, 116.4, 107.5, 101.5, 108.5, 109.3, 119, 111.3, 108.5, 117.5, 84.7, 107, 121.8, 117.7, 116, 108.5,
        118.4, 113, 122.5, 117.1, 112, 122.6, 90.2, 112.3, 122.4, 125.4, 120.7, 107.2, 126.8, 118.8, 132.9, 117.7, 121.8, 123.9, 90.3, 113.2, 124.7, 135.4, 126.3, 110.1,
        126.8, 117.7, 126.6, 123, 118.1, 123.7, 93.5, 105.4, 125, 131.9, 119.9, 110.3, 126.2, 121.6, 130.9, 123.6, 116.1, 126.9, 95, 107.6, 128.4, 127.1, 116.3, 109.5,
        113.4, 114, 128.5, 118.3, 108.6, 124.2, 86.7, 104.2, 124.1, 121.2, 112.6, 114.1, 120.3, 117.6, 133.6, 117.7, 113.8, 126.6, 81.6, 108.7, 125.9, 123, 120.7, 109.7};

    public static final double[] M1
            = {
                1320.7, 1353.9, 1604.3, 1335.2, 1365.7, 1578.3, 1160.5, 1161.9, 1450.8, 1462, 1431.5, 1396.9,
                1375.6, 1445.4, 1548, 1477.4, 1488, 1486.9, 1260.4, 1171.6, 1585.7, 1668.5, 1446.5, 1505.4,
                1399.5, 1456.7, 1554.9, 1613.1, 1543.5, 1599.3, 1402.7, 1147.5, 1629, 1683.3, 1415.2, 1757,
                1497.5, 1575.2, 1718.8, 1628.4, 1446.6, 1679.7, 1456.2, 1336.6, 1714.3, 1771.7, 1662.6, 1742.2,
                1461.3, 1617.1, 1826.8, 1620.8, 1514.3, 1797.5, 1500.6, 1455.9, 1743.9, 1833, 1761.5, 1974.1,
                1671.8, 1841.5, 2126.9, 1716.3, 2005.8, 1840.8, 1733.4, 1451.7, 1957, 2127.9, 2094.3, 2157.1,
                2160.3, 1994.5, 2225.3, 2015.6, 2044.5, 2257.8, 1810.4, 1666.3, 2235.2, 2091.1, 2093.9, 1968.2,
                1962.3, 2095.2, 2161, 2115.1, 1929, 2004.5, 2009.9, 1524.9, 2061.1, 2261.6, 2103.6, 2224.3, 2173.8,
                2119.2, 2226.4, 2159.6, 1918.3, 2116.1, 1948.3, 1514.3, 2180.5, 2312.6, 2019.8, 2200.8, 2028.9,
                2178.7, 2433.7, 2230.5, 1884.2, 2372.7, 1918.6, 1679.4, 2327.3, 2225.2, 2211.7, 2463.6, 2029.5,
                2173.6, 2387, 2234, 2179.9, 2397, 1960.2, 1824.1, 2479.3, 2234.9, 2345.9, 2428.9, 2179.4, 2216.9,
                2642.3, 2340.5, 2474.6, 2641.8, 2165.1, 1996.2, 2562.9, 2529.9, 2549.6, 2455.1, 2472, 2424.7,
                2820.1, 2482.8, 2509.8, 2668.6, 2498.3, 2056.9, 2559.4, 2852.7, 2465.9, 2462.9, 2577, 2738.9,
                2771.8, 2954.7, 2525.3, 3163.9, 2720.1, 2233.5, 2972.4, 2941.8, 2171.7
            };

    public static final double[] M2
            = {
                1619.4, 1655, 1863.2, 1595.3, 1621.4, 1761.1, 1328.3, 1547.5, 1740.3, 1727, 1775.5, 1778.5, 1738.7,
                1798, 2045, 1808.6, 1809.8, 1897.7, 1605.2, 1730.8, 2013, 2061, 1765.6, 2083.2, 1961.4, 1960.6,
                2141.5, 1961.6, 1955.7, 2126.3, 1830.7, 1835.9, 2171.9, 2262.8, 2057.5, 2350.5, 1990.6, 2027.2,
                2204.9, 2020.3, 1906.4, 2034.8, 1802.9, 1724, 2078.3, 2020.4, 1907.6, 2055.7, 1707.6, 1801,
                2222.8, 1996.1, 1857, 2164.3, 1799.7, 1946.1, 2286.1, 2287.2, 2334, 2640.6, 2404.7, 2540.2, 2934.3,
                2533.3, 2689.8, 2596.7, 2321.7, 2746.6, 2867.8, 2881.4, 3194, 3146.5, 2912.9, 2924.3, 2935.6,
                3022.4, 2970.6, 2862.1, 2508, 2636.3, 2691.4, 2717.7, 2494.2, 2599.7, 2595.3, 2576, 2821.1,
                2807.3, 2725.4, 2617.5, 2521.4, 2463.2, 2808.6, 2993.2, 2605.6, 2856.4, 2861.5, 2809.3, 3092.4,
                2671.3, 2568.8, 2656.9, 2547.8, 2408.4, 2818.7, 2871.5, 2779.8, 3009.6, 2764, 2788.5, 3319.4,
                2998.2, 2841.2, 3233.5, 2889.3, 2910.5, 3259.3, 3419.9, 3311.8, 3644.9, 3208.3, 3400.6, 3969.6,
                3657.2, 3268.7, 3486.7, 3121.8, 3544.2, 3840.6, 3725.7, 4304.1, 4887.5, 4370, 4343.9, 5546,
                3953.4, 4115.5, 3964.8, 3651, 4032.1, 3862.5, 3993.1, 3963, 3962.3, 3910.2, 3685.9, 4055.5,
                3584.7, 4035.5, 4188.1, 4142.8, 4142.1, 4335.1, 4792.7, 4984.9, 5027.9, 5087.6, 4881.2, 5287.7,
                5299.5, 5075.3, 5779.7, 5245.9, 5103.1, 5285.6, 5221.1, 4348.7
            };

    public static final double[] M3
            = {
                1661.8, 1736.9, 2233.7, 1925, 1938.8, 2017.7, 1442.7, 1673.8, 1887.8, 1957.7, 1930.4, 1737.8,
                1815.1, 1888.1, 1950.6, 1806.2, 1746.8, 1778.2, 1502, 1541.1, 1876.6, 1979.7, 1777.5, 1716.7,
                1689.9, 1805.9, 2006.4, 2004.9, 1740, 2014.5, 1639.4, 1561, 2000.7, 1968.2, 1825.5, 1846.9,
                1714.8, 1936.7, 2194.8, 2105.3, 1949.7, 2150.1, 1864.1, 1873.6, 2107.7, 2077.4, 2007.4, 1975.8,
                1737, 1844.1, 2216.8, 1982, 1816.9, 2155.1, 1632.5, 1851, 2147.4, 2163.3, 2192, 2251.7, 2004.3,
                2429.2, 2641.8, 2203.7, 2504.1, 2280.4, 2054.3, 2185.2, 2406.8, 2437.7, 2606.2, 2350.6, 2386.5,
                2469.6, 2785.1, 2334.1, 2388.2, 2379.8, 2003.1, 2023.9, 2276.4, 2420.3, 2361.2, 2241.3, 2171.7,
                2293.9, 2493.7, 2382.5, 2286, 2391.8, 2163.6, 2095.9, 2442.1, 2611.2, 2498.6, 2342.2, 2326.8,
                2417.4, 2572.8, 2403.6, 2294.1, 2353.6, 2201.2, 1925.8, 2428.8, 2603.2, 2330.1, 2482.9, 2255.6,
                2518, 2960.7, 2571.5, 2348.4, 2817.9, 2166.6, 2284.7, 2864.3, 2738.3, 2734.9, 2893.3, 2503.3,
                2685.1, 3034.9, 2826.9, 2529.1, 2867.3, 2202.9, 2401.3, 2869.8, 2589, 2945.2, 2896.6, 2809.3,
                2926.4, 3634.7, 2772.1, 3023.5, 3022.9, 2565.5, 2797.5, 3101, 3092.9, 3140.5, 2751.5, 2947.4,
                3128.4, 3569, 2991.1, 3217.3, 3309.6, 2924.5, 2881.1, 3113.6, 3350.3, 3236.7, 3058.2, 3330,
                3437.9, 3536.9, 3707.8, 3316.2, 3697.6, 3199.1, 2929.6, 3468.5, 3620.7, 3065.6
            };

    public static final double[] PCRA = {
        16094.34042, 16368.12021, 15233.88966, 15370.77955, 15683.67074, 16407.23161, 16876.56839, 17424.12796,
        18206.35593, 19340.58648, 19555.69917, 19916.36579, 20482.17633, 21100.69323, 22038.76268/*,23006.35799,24479.42141
     ,26286.34352,28357.72638,30543.23084*/
    };

    public static final double[] IND_PCR = {
        103.29532, 102.75762, 101.91172, 102.58308, 103.07061, 102.87651, 104.22476, 95.163725, 95.500964,
        94.947974, 94.477737, 93.422194, 94.20844, 91.778527, 92.600797, 92.313678, 91.358591, 92.104858,
        90.402934, 91.4894, 90.6345, 92.098056, 91.939821, 92.283861, 92.091555, 91.701768, 92.288204,
        91.592825, 91.216143, 91.431754, 90.986469, 91.359825, 91.535054, 91.736206, 93.27483, 94.382133,
        95.025179, 97.090378, 96.715138, 98.10199, 99.148287, 99.172499, 100.04511, 99.498929, 99.061791,
        99.666563, 99.600238, 100.33003, 100.71723, 101.44431, 101.94025, 102.34955, 102.13386, 103.151,
        105.14168, 105.66517, 108.35956, 107.9938, 109.07239, 109.41624, 110.01066, 111.35785, 112.7508,
        114.25636, 115.12613, 116.84717, 116.93883, 119.16994, 121.01932, 123.56063, 125.66876, 128.84939,
        131.31864, 133.76343, 135.89239, 137.78569, 142.49302, 143.08843, 145.92117, 148.21557
    };

    public static final double[] NILE = new double[]{
        1120, 1160, 963, 1210, 1160, 1160, 813, 1230, 1370, 1140, 995, 935, 1110, 994, 1020, 960, 1180,
        799, 958, 1140, 1100, 1210, 1150, 1250, 1260, 1220, 1030, 1100, 774, 840, 874, 694, 940, 833,
        701, 916, 692, 1020, 1050, 969, 831, 726, 456, 824, 702, 1120, 1100, 832, 764, 821, 768, 845,
        864, 862, 698, 845, 744, 796, 1040, 759, 781, 865, 845, 944, 984, 897, 822, 1010, 771, 676,
        649, 846, 812, 742, 801, 1040, 860, 874, 848, 890, 744, 749, 838, 1050, 918, 986, 797, 923,
        975, 815, 1020, 906, 901, 1170, 912, 746, 919, 718, 714, 740};

    public static final double[] US_UNEMPL = new double[]{
        4.4, 3.77, 3.63, 3.27,
        5.47, 5.83, 6.4, 5.93,
        7.53, 5.63, 4.47, 3.67,
        4.13, 3.17, 3.07, 2.97,
        3.6, 3.03, 3.03, 2.47,
        3.17, 2.67, 2.57, 3.3,
        6.13, 5.83, 5.47, 4.77,
        5.57, 4.5, 3.77, 3.7,
        4.73, 4.33, 3.83, 3.67,
        4.63, 4.17, 3.83, 4.4,
        7.4, 7.4, 6.7, 5.7,
        6.8, 4.87, 4.9, 5.03,
        5.97, 5.27, 5.13, 5.67,
        7.83, 6.97, 6.3, 5.63,
        6.47, 5.5, 5.17, 5.03,
        6.6, 5.77, 5.2, 5.1,
        6.17, 5.07, 4.73, 4.53,
        5.43, 4.77, 4.17, 3.73,
        4.2, 3.97, 3.6, 3.37,
        4.1, 3.77, 3.83, 3.67,
        4, 3.53, 3.6, 3.2,
        3.63, 3.4, 3.67, 3.33,
        4.5, 4.67, 5.17, 5.4,
        6.5, 5.83, 5.97, 5.53,
        6.3, 5.6, 5.57, 4.9,
        5.43, 4.83, 4.8, 4.4,
        5.53, 5.07, 5.53, 6.13,
        9.07, 8.67, 8.33, 7.8,
        8.53, 7.37, 7.6, 7.33,
        8.23, 6.93, 6.8, 6.27,
        6.83, 5.83, 5.9, 5.53,
        6.2, 5.57, 5.77, 5.6,
        6.73, 7.13, 7.5, 7.03,
        7.97, 7.27, 7.27, 7.9,
        9.5, 9.37, 9.7, 10.27,
        11.17, 10, 9.13, 8.17,
        8.43, 7.4, 7.3, 6.97,
        7.77, 7.2, 7.07, 6.73
    };

    public static final TsData TS_PROD;

    static {
        TS_PROD = TsData.of(TsPeriod.of(TsFrequency.Monthly, 1967, 0), DoubleSequence.ofInternal(PROD));
    }
}