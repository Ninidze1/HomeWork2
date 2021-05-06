import kotlin.math.pow

// აუ პირობას კარგად ბოლოსკენ შევხედე, 1000 ის მაგივრად მილიონამდე ითვლის ეს კოდი, შემენანა წაშლა :დდდ

fun main() {

    println(toGeoText(44))
    println(toGeoText(99))
    println(toGeoText(180))
    println(toGeoText(1097))
    println(toGeoText(18021))
    println(toGeoText(101010))

}

fun toGeoText(num: Int): String {

    val join = "და"
    val numNames: Map<Int, String> = mapOf(
        1 to "ერთი", 2 to "ორი", 3 to "სამი", 4 to "ოთხი", 5 to "ხუთი", 6 to "ექვსი",
        7 to "შვიდი", 8 to "რვა", 9 to "ცხრა", 10 to "ათი", 11 to "თერთმეტი",
        12 to "თორმეტი", 13 to "ცამეტი", 14 to "თოთხმეტი", 15 to "თხუთმეტი",
        16 to "თექვსმეტი", 17 to "ჩვიდმეტი", 18 to "თვრამეტი", 19 to "ცხრამეტი",
        20 to "ოცი", 30 to "ოცდაათი", 40 to "ორმოცი", 50 to "ორმოცდაათი", 60 to "სამოცი",
        70 to "სამოცდაათი", 80 to "ოთხმოცი", 90 to "ოთხმოცდაათი", 100 to "ასი", 1000 to "ათასი",
        1000000 to "მილიონი", 0 to "ნული"
    )

    val base = 10.0
    val tempForPow: Int = num.toString().length

    val result: String
    val firstDigit: Int = num.toString().first().toString().toInt()
    // ცვლადი რომელიც აბრუნებს 10^(გადაცემულ რიცხვის სიგრძე)
    val temp: Int = (firstDigit * (base.pow((tempForPow - 1).toDouble()))).toInt()

    // ეს ცვლადი ამოწმებს არის თუ არა გადაცემული მნიშვნელობა შემდეგი სახის, მაგ 100, 4000, 500000 და ა.შ ,ანუ ის შეიცავს ერთი ციფრის გარდა ყელა ნულიანს
    val checker: Boolean =
        num.toString().removePrefix(firstDigit.toString()).toCharArray().toSet().joinToString("") == "0"

    // ლოგიკა 2 ნიშნა რიცხვებისთვის
    result = if (num !in numNames.keys && (((num - num % 10) / 10) % 2 == 0) && num < 100)
        numNames[num - num % 10].toString().dropLast(1) + join + numNames[num % 10].toString()
    else if (num !in numNames.keys && ((num - num % 10) / 10) % 2 == 1 && num < 100) {
        numNames[(firstDigit - 1) * 10].toString().dropLast(1) + join + numNames[num - (firstDigit - 1) * 10]
    }

    // ლოგიკა 3 ნიშნა რიცხვებისთვის
    else if (num in 101 until 1000 && num.toString().length == 3 && (firstDigit != 1 && firstDigit != 8 && firstDigit != 9) && !checker) {
        numNames[firstDigit].toString().dropLast(1) + numNames[100].toString()
            .dropLast(1) + " " + toGeoText(num - firstDigit * 100)
    } else if (num in 101 until 1000 && num.toString().length == 3 && firstDigit != 1 && (firstDigit == 8 || firstDigit == 9) && !checker) {
        numNames[firstDigit].toString() + numNames[100].toString().dropLast(1) + " " + toGeoText(num - temp)
    }

    // ლოგიკა სამნიშნა  რიცხვებისთვის რომელსაც ერთის გარდა ყველა თანრიგში ნულიანი აქვს
    else if (num.toString().length == 3 && checker && num != 100) {
        numNames[firstDigit].toString().dropLast(1) + numNames[100]
    }

    // ლოგიკა დამრგვალებული რიცხვებისთვის, რომელთა სიგრძეებია 5 ან 6
    else if (num.toString().length >= 4 && checker  && num != 1000000) {
        when (num.toString().length) {
            4 -> toGeoText(num.toString().substring(0..0).toInt()) + " " + numNames[1000]
            5 -> toGeoText(num.toString().substring(0..1).toInt()) + " " + numNames[1000]
            6 -> toGeoText(num.toString().substring(0..2).toInt()) + " " + numNames[1000]
            else -> "Error"
        }

        // ლოგიკა სამნიშვნა რიცხვებისთვის რომელიც 1 ით იწყება
    }
    else if (num in 101 until 1000 && num.toString().length == 3 && firstDigit == 1) {
        numNames[100].toString().dropLast(1) + " " + toGeoText(num - temp)
    }
    // ლოგიკა ოთხნიშნა რიცხვებისთვის, რომლებიც არ იწყებიან 1 ზე
    else if (num in 1001 until 10000 && firstDigit != 1) {
        numNames[firstDigit].toString() + " " + numNames[1000].toString().dropLast(1) + " " +
                toGeoText(num - temp)
    }
    // 1 ზე დაწყებული ოთხნიშნა რიცხვების ლოგიკა
    else if (num in 1001 until 10000 && firstDigit == 1) {
        numNames[1000].toString().dropLast(1) + " " + toGeoText(num - temp)
    }
    // 5 ნიშნა რიცხვების ლოგიკა
    else if (num in 10001 until 100000 && num.toString().length == 5) {
        toGeoText(num.toString().substring(0..1).toInt()) + " " + numNames[1000].toString().dropLast(1) +
                " " + toGeoText(num.toString().substring(2..4).toInt())
    }
    // 6 ნიშნა რიცხვების ლოგიკა
    else if (num > 100000 && num.toString().length == 6) {
        toGeoText(num.toString().substring(0..2).toInt()) + numNames[1000].toString().dropLast(1) +
                " " + toGeoText(num.toString().substring(3..5).toInt())
    }
    // თუ შეყვანილი მონაცემი საწყის მაპში მოიძებნება
    else numNames[num].toString()

    return result

}
