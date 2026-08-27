package com.example.data.provider

import com.example.data.model.*

object ExpandedBookData {

    val additionalReasoningChapters = listOf(
        Chapter(
            id = "reas_04",
            subjectType = SubjectType.REASONING,
            titleHindi = "4. Blood Relations",
            titleEnglish = "Blood Relations",
            chapterNumber = 4,
            description = "Family tree diagrams, generation rules, and step-by-step problem-solving shortcuts.",
            conceptExplanation = """
                Blood Relation questions test your ability to trace family tree connections logically.

                Generation Tree Mapping Rules:
                1. Same Generation: Brother, Sister, Husband, Wife, Cousin.
                2. One Generation Above: Father, Mother, Uncle, Aunt, Father-in-law, Mother-in-law.
                3. Two Generations Above: Grandfather, Grandmother.
                4. One Generation Below: Son, Daughter, Nephew, Niece, Son-in-law.

                Symbolic Representation:
                • Male = [+] Square Box
                • Female = [-] Circle
                • Married Couple = Double Line [=]
                • Siblings = Single Line [-]
            """.trimIndent(),
            solvedExamples = listOf(
                SolvedExample(
                    id = 1,
                    questionText = "Q1. Pointing to a photograph, Ram said, 'He is the only son of my mother's husband.' How is the person in the photo related to Ram?",
                    solutionText = "Solution: Ram's mother's husband = Ram's father. Father's only son = Ram himself. Answer: Ram himself.",
                    shortTrickText = "Relate the statement directly to yourself."
                )
            ),
            practiceQuestions = listOf(
                Question(
                    id = "reas_q6",
                    chapterId = "reas_04",
                    subjectType = SubjectType.REASONING,
                    questionText = "A is the brother of B. C is the mother of A. D is the father of C. How is A related to D?",
                    optionA = "Grandson",
                    optionB = "Son",
                    optionC = "Father",
                    optionD = "Brother",
                    correctOptionIndex = 0,
                    detailedSolution = "A and B are siblings. C is their mother and D is their mother's father (Grandfather). Since A is male, A is the Grandson of D. Option (A) is correct.",
                    difficultyLevel = DifficultyLevel.MEDIUM
                )
            )
        ),
        Chapter(
            id = "reas_05",
            subjectType = SubjectType.REASONING,
            titleHindi = "5. Number & Alphabet Series",
            titleEnglish = "Number & Alphabet Series",
            chapterNumber = 5,
            description = "Identifying pattern rules, difference methods, square/cube patterns, and series shortcuts.",
            conceptExplanation = """
                Series completion requires identifying the underlying pattern sequence.

                Key Series Patterns:
                1. Arithmetic Series (Constant Difference): +2, +4, +6...
                2. Alternate Series: Skipping alternate terms to form sub-sequences.
                3. Square / Cube Series: 1, 4, 9, 16, 25... or 1, 8, 27, 64...
                4. Geometric / Multiplicative Series: ×2+1, ×2+2...
            """.trimIndent(),
            solvedExamples = listOf(
                SolvedExample(
                    id = 1,
                    questionText = "Q1. Complete the series: 2, 6, 12, 20, 30, ?",
                    solutionText = "Solution: 2(+4)=6, 6(+6)=12, 12(+8)=20, 20(+10)=30, 30(+12)=42. Answer: 42.",
                    shortTrickText = "Difference of differences: +4, +6, +8, +10, +12..."
                )
            ),
            practiceQuestions = listOf(
                Question(
                    id = "reas_q7",
                    chapterId = "reas_05",
                    subjectType = SubjectType.REASONING,
                    questionText = "What will be the next number in the series? 3, 7, 15, 31, ?",
                    optionA = "63",
                    optionB = "60",
                    optionC = "62",
                    optionD = "65",
                    correctOptionIndex = 0,
                    detailedSolution = "Pattern: (Previous Term × 2) + 1. 3×2+1=7, 7×2+1=15, 15×2+1=31, 31×2+1=63. Correct option is (A) 63.",
                    difficultyLevel = DifficultyLevel.EASY
                )
            )
        )
    )

    val additionalMathsChapters = listOf(
        Chapter(
            id = "math_03",
            subjectType = SubjectType.MATHEMATICS,
            titleHindi = "3. Profit, Loss & Discount",
            titleEnglish = "Profit, Loss & Discount",
            chapterNumber = 3,
            description = "Cost price, selling price, marked price, discount formulas, and shortcut tricks.",
            conceptExplanation = """
                Terminology:
                • Cost Price (CP): Price at which an article is bought.
                • Selling Price (SP): Price at which an article is sold.
                • Marked Price (MP): List price printed on the article label.

                Core Formulas:
                • Profit = SP - CP | Loss = CP - SP
                • Profit % = (Profit / CP) × 100
                • Loss % = (Loss / CP) × 100
                • Discount = MP - SP
                • Discount % = (Discount / MP) × 100
            """.trimIndent(),
            formulas = listOf(
                FormulaBox(
                    title = "Selling Price (SP) Formula",
                    formulaText = "SP = [CP × (100 + Profit%)] / 100",
                    explanation = "Direct calculation of Selling Price when Cost Price and Profit % are given."
                )
            ),
            solvedExamples = listOf(
                SolvedExample(
                    id = 1,
                    questionText = "Q1. An article with cost price ₹800 is sold for ₹1,000. Find the profit percentage?",
                    solutionText = "Solution: Profit = 1000 - 800 = ₹200. Profit % = (200 / 800) × 100 = 25%. Answer: 25%.",
                    shortTrickText = "Fraction 200/800 = 1/4 = 25%."
                )
            ),
            practiceQuestions = listOf(
                Question(
                    id = "math_q4",
                    chapterId = "math_03",
                    subjectType = SubjectType.MATHEMATICS,
                    questionText = "If the marked price of an item is ₹1,500 and a 20% discount is offered, what will be the selling price?",
                    optionA = "₹1,200",
                    optionB = "₹1,300",
                    optionC = "₹1,100",
                    optionD = "₹1,250",
                    correctOptionIndex = 0,
                    detailedSolution = "Discount = 1500 × 20/100 = ₹300. Selling Price = 1500 - 300 = ₹1,200. Correct option is (A) ₹1,200.",
                    difficultyLevel = DifficultyLevel.EASY
                )
            )
        ),
        Chapter(
            id = "math_04",
            subjectType = SubjectType.MATHEMATICS,
            titleHindi = "4. Time, Speed & Distance",
            titleEnglish = "Time, Speed & Distance",
            chapterNumber = 4,
            description = "Speed = Distance / Time, average speed, train problems, and unit conversions.",
            conceptExplanation = """
                Core Formulas:
                • Speed = Distance / Time
                • Distance = Speed × Time
                • Time = Distance / Speed

                Unit Conversions:
                • Convert km/h to m/s: Multiply by 5/18.
                • Convert m/s to km/h: Multiply by 18/5.

                Average Speed:
                If a journey is covered at x km/h and returned at y km/h:
                Average Speed = (2 × x × y) / (x + y)
            """.trimIndent(),
            formulas = listOf(
                FormulaBox(
                    title = "Unit Conversion Formula",
                    formulaText = "1 Km/h = 5/18 m/s | 1 m/s = 18/5 Km/h",
                    explanation = "Standard unit conversion factors for speed."
                )
            ),
            solvedExamples = listOf(
                SolvedExample(
                    id = 1,
                    questionText = "Q1. Convert 72 km/h into meters per second (m/s)?",
                    solutionText = "Solution: 72 × (5/18) = 4 × 5 = 20 m/s. Answer: 20 m/s.",
                    shortTrickText = "18 × 4 = 72; so 5 × 4 = 20 m/s."
                )
            ),
            practiceQuestions = listOf(
                Question(
                    id = "math_q5",
                    chapterId = "math_04",
                    subjectType = SubjectType.MATHEMATICS,
                    questionText = "A train moves at a speed of 90 km/h. What distance will it cover in 20 seconds?",
                    optionA = "500 meters",
                    optionB = "450 meters",
                    optionC = "600 meters",
                    optionD = "400 meters",
                    correctOptionIndex = 0,
                    detailedSolution = "Speed in m/s = 90 × (5/18) = 25 m/s. Distance = Speed × Time = 25 × 20 = 500 meters. Correct option is (A) 500 meters.",
                    difficultyLevel = DifficultyLevel.EASY
                )
            )
        ),
        Chapter(
            id = "math_05",
            subjectType = SubjectType.MATHEMATICS,
            titleHindi = "5. Simple & Compound Interest (SI & CI)",
            titleEnglish = "Simple & Compound Interest",
            chapterNumber = 5,
            description = "Simple interest, compound interest formula, 2-year difference shortcuts.",
            conceptExplanation = """
                1. Simple Interest (SI):
                SI = (P × R × T) / 100
                (P = Principal, R = Rate %, T = Time in years)

                2. 2-Year Difference between CI and SI:
                Difference = P × (R / 100)²
            """.trimIndent(),
            formulas = listOf(
                FormulaBox(
                    title = "2-Year CI and SI Difference Formula",
                    formulaText = "D = P × (R / 100)²",
                    explanation = "Difference between Compound Interest and Simple Interest for 2 years."
                )
            ),
            solvedExamples = listOf(
                SolvedExample(
                    id = 1,
                    questionText = "Q1. Find the Simple Interest on ₹5,000 at 10% per annum for 2 years?",
                    solutionText = "Solution: SI = (5000 × 10 × 2) / 100 = ₹1,000. Answer: ₹1,000.",
                    shortTrickText = "10% × 2 = 20%. 20% of 5000 = 1000."
                )
            ),
            practiceQuestions = listOf(
                Question(
                    id = "math_q6",
                    chapterId = "math_05",
                    subjectType = SubjectType.MATHEMATICS,
                    questionText = "What will be the Simple Interest earned on ₹10,000 at 10% per annum for 3 years?",
                    optionA = "₹3,000",
                    optionB = "₹2,500",
                    optionC = "₹3,500",
                    optionD = "₹3,310",
                    correctOptionIndex = 0,
                    detailedSolution = "SI = (P × R × T) / 100 = (10000 × 10 × 3) / 100 = ₹3,000. Correct option is (A) ₹3,000.",
                    difficultyLevel = DifficultyLevel.EASY
                )
            )
        )
    )
}
