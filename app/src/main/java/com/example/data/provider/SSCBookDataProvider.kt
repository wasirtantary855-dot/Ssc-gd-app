package com.example.data.provider

import com.example.data.model.*

object SSCBookDataProvider {

    val allSubjects: List<SubjectType> = SubjectType.values().toList()

    val introChapters: List<Chapter> = listOf(
        Chapter(
            id = "intro_01",
            subjectType = SubjectType.INTRO,
            titleHindi = "1. SSC GD Constable Exam Overview",
            titleEnglish = "SSC GD Exam Overview & Pattern",
            chapterNumber = 1,
            description = "Complete details of SSC GD Constable exam, eligibility criteria, selection process, and marking scheme.",
            conceptExplanation = """
                The Staff Selection Commission (SSC) conducts the General Duty (GD) Constable exam annually for recruitment into Central Armed Police Forces (CAPFs - BSF, CISF, CRPF, SSB, ITBP, Assam Rifles, SSF).

                1. Eligibility Criteria:
                • Educational Qualification: Passed 10th Class (Matriculation) from a recognized Board.
                • Age Limit: 18 to 23 years (Upper age relaxation of 5 years for SC/ST and 3 years for OBC as per government rules).

                2. Selection Process Stages:
                Stage 1: Computer Based Examination (CBE / CBT)
                Stage 2: Physical Efficiency Test (PET) & Physical Standard Test (PST)
                Stage 3: Detailed Medical Examination (DME)
                Stage 4: Document Verification (DV)

                3. Computer Based Test (CBT) Pattern:
                • Total Questions: 80 Multiple Choice Questions (MCQs)
                • Total Time: 60 minutes (1 hour)
                • Total Marks: 160 marks (2 marks for each correct answer)
                • Exam Parts (4 Sections):
                  - Part A: General Intelligence & Reasoning -> 20 Questions (40 Marks)
                  - Part B: General Knowledge & General Awareness -> 20 Questions (40 Marks)
                  - Part C: Elementary Mathematics -> 20 Questions (40 Marks)
                  - Part D: English / Hindi Language -> 20 Questions (40 Marks)

                4. Negative Marking Scheme:
                • 0.25 marks will be deducted for each incorrect answer.
                • Attempt only those questions where you have high confidence.

                5. PET / PST Physical Standards:
                • PET Running Test:
                  - Male: 5 km in 24 minutes
                  - Female: 1.6 km in 8.5 minutes
                • PST Height:
                  - General/OBC/SC Male: 170 cm | Female: 157 cm
                  - ST Male: 162.5 cm | ST Female: 150 cm
                • PST Chest (Male): 80 cm (minimum 5 cm expansion compulsory)
            """.trimIndent(),
            rulesAndConcepts = listOf(
                "Avoid blind guessing to minimize negative marking (0.25 penalty per wrong response).",
                "Mathematics and Reasoning offer high scoring potential; focus on accuracy and speed.",
                "Time management is critical in CBT mode: 80 questions in 60 minutes."
            ),
            shortTricks = listOf(
                "Time Allocation Rule: Reasoning (15 mins), General Knowledge (8 mins), Language (10 mins), Mathematics (25 mins) with a 2-minute buffer.",
                "Combine written exam practice with 30 minutes of daily morning running practice to clear PET effortlessly."
            ),
            revisionFacts = listOf(
                "Mode of Exam: Computer Based Test (CBT)",
                "Pattern: 80 Questions | 160 Marks | 60 Minutes",
                "Negative Penalty: 0.25 marks per wrong answer",
                "PET Run: Male 5km in 24 min | Female 1.6km in 8.5 min"
            )
        )
    )

    val reasoningChapters: List<Chapter> = listOf(
        Chapter(
            id = "reas_01",
            subjectType = SubjectType.REASONING,
            titleHindi = "1. Analogy (Word, Number & Alphabet)",
            titleEnglish = "Analogy",
            chapterNumber = 1,
            description = "Concepts, rules, shortcuts, and solved examples for Word, Number, and Alphabet Analogies.",
            conceptExplanation = """
                Analogy means 'similarity' or 'bearing a identical relation'. In analogy questions, two elements share a specific logical relationship, and you must identify the fourth missing element that shares the exact same relationship with the third element.

                Types of Analogy:
                1. Word Analogy: e.g., India : New Delhi :: France : ? (Answer: Paris)
                2. Number Analogy: e.g., 5 : 25 :: 7 : ? (Answer: 49)
                3. Alphabet Analogy: e.g., ABC : ZYX :: DEF : ? (Answer: WVU)
            """.trimIndent(),
            rulesAndConcepts = listOf(
                "Priority order in Number Analogy: 1. Prime Numbers -> 2. Squares/Cubes -> 3. Multiplication/Division -> 4. Addition/Subtraction.",
                "Memorize reverse alphabet position pairs: A-Z, B-Y, C-X, D-W, E-V, F-U, G-T, H-S, I-R, J-Q, K-P, L-O, M-N."
            ),
            formulas = listOf(
                FormulaBox(
                    title = "Alphabet Positions (EJOTY Rule)",
                    formulaText = "E=5, J=10, O=15, T=20, Y=25",
                    explanation = "Use the EJOTY benchmark rule to quickly locate letter position numbers in the English alphabet."
                )
            ),
            shortTricks = listOf(
                "If numbers jump sharply, look for Square, Cube, or Multiplication patterns first."
            ),
            solvedExamples = listOf(
                SolvedExample(
                    id = 1,
                    questionText = "Q1. 8 : 64 :: 10 : ?",
                    solutionText = "Solution: 8² = 64. Similarly, 10² = 100. Answer: 100.",
                    shortTrickText = "8 squared is 64; therefore 10 squared is 100."
                ),
                SolvedExample(
                    id = 2,
                    questionText = "Q2. Doctor : Hospital :: Teacher : ?",
                    solutionText = "Solution: A doctor's workplace is a hospital. Similarly, a teacher's workplace is a School.",
                    shortTrickText = "Relationship: Person -> Workplace"
                )
            ),
            practiceQuestions = listOf(
                Question(
                    id = "reas_q1",
                    chapterId = "reas_01",
                    subjectType = SubjectType.REASONING,
                    questionText = "Select the related word from the given options: Pen : Writing :: Knife : ?",
                    optionA = "Cutting",
                    optionB = "Sewing",
                    optionC = "Vegetable",
                    optionD = "Kitchen",
                    correctOptionIndex = 0,
                    detailedSolution = "A pen is used for writing; similarly, a knife is used for cutting. Option (A) Cutting is correct.",
                    difficultyLevel = DifficultyLevel.EASY
                ),
                Question(
                    id = "reas_q2",
                    chapterId = "reas_01",
                    subjectType = SubjectType.REASONING,
                    questionText = "Complete the analogy: 12 : 144 :: 15 : ?",
                    optionA = "225",
                    optionB = "210",
                    optionC = "180",
                    optionD = "250",
                    correctOptionIndex = 0,
                    detailedSolution = "12 squared is 144 (12 × 12). Similarly, 15 squared is 225 (15 × 15). Correct answer is (A).",
                    difficultyLevel = DifficultyLevel.EASY
                ),
                Question(
                    id = "reas_q3",
                    chapterId = "reas_01",
                    subjectType = SubjectType.REASONING,
                    questionText = "Alphabet Analogy: CAT : DDW :: DOG : ?",
                    optionA = "ERJ",
                    optionB = "EQJ",
                    optionC = "FQK",
                    optionD = "EPI",
                    correctOptionIndex = 0,
                    detailedSolution = "Pattern: C(+1)=D, A(+3)=D, T(+3)=W. Applying to DOG: D(+1)=E, O(+3)=R, G(+3)=J. Answer is ERJ (Option A).",
                    difficultyLevel = DifficultyLevel.MEDIUM
                )
            )
        ),
        Chapter(
            id = "reas_02",
            subjectType = SubjectType.REASONING,
            titleHindi = "2. Coding-Decoding",
            titleEnglish = "Coding-Decoding",
            chapterNumber = 2,
            description = "Letter shift, number coding, and substitution rules with shortcut techniques.",
            conceptExplanation = """
                In Coding-Decoding questions, letters, words, or numbers are transformed into coded form following a specific rule. You need to decode the underlying logic and apply it to find the answer.

                Common Types:
                1. Letter Shift Coding: +1, -1, +2, -2 position shifts.
                2. Opposite Letter Coding: A↔Z, B↔Y, C↔X pairs.
                3. Number Positional Coding: Sum of letter positions or direct numeric substitution.
            """.trimIndent(),
            rulesAndConcepts = listOf(
                "Mnemonic for opposite pairs: Azad(A-Z), Boy(B-Y), Cox(C-X), Dew(D-W), Even(E-V), Full(F-U), GT-Road(G-T), High-School(H-S), Indian-Railway(I-R), Jack-Queen(J-Q), Kanpur(K-P), Light-Out(L-O), Man(M-N)."
            ),
            shortTricks = listOf(
                "When a word is coded into numbers, test positional value sum (A=1...Z=26) first."
            ),
            solvedExamples = listOf(
                SolvedExample(
                    id = 1,
                    questionText = "Q1. If 'BOY' is coded as 'CPZ' in a certain code, how is 'GIRL' coded?",
                    solutionText = "Solution: B(+1)=C, O(+1)=P, Y(+1)=Z. Thus, G(+1)=H, I(+1)=J, R(+1)=S, L(+1)=M -> HJSM.",
                    shortTrickText = "+1 shift applied to each letter."
                )
            ),
            practiceQuestions = listOf(
                Question(
                    id = "reas_q4",
                    chapterId = "reas_02",
                    subjectType = SubjectType.REASONING,
                    questionText = "If 'MANGO' is coded as 51, what will be the code for 'APPLE'?",
                    optionA = "50",
                    optionB = "45",
                    optionC = "55",
                    optionD = "60",
                    correctOptionIndex = 0,
                    detailedSolution = "Positional values of MANGO: M(13) + A(1) + N(14) + G(7) + O(15) = 50 + 1 = 51. For APPLE: A(1) + P(16) + P(16) + L(12) + E(5) = 50. Correct option is (A) 50.",
                    difficultyLevel = DifficultyLevel.MEDIUM
                )
            )
        ),
        Chapter(
            id = "reas_03",
            subjectType = SubjectType.REASONING,
            titleHindi = "3. Direction & Distance Test",
            titleEnglish = "Direction & Distance",
            chapterNumber = 3,
            description = "Cardinal directions, turning rules, Pythagoras theorem, and shortcut tricks.",
            conceptExplanation = """
                Direction tests involve 4 cardinal directions (North, South, East, West) and 4 sub-directions (North-East, South-East, North-West, South-West).
                Right Turn = 90° Clockwise
                Left Turn = 90° Anti-Clockwise

                Pythagoras Theorem for shortest distance:
                Hypotenuse² = Base² + Perpendicular² (d = √(x² + y²))
            """.trimIndent(),
            solvedExamples = listOf(
                SolvedExample(
                    id = 1,
                    questionText = "Q1. Rahul walks 4 km East, turns right and walks 3 km. How far is he from the starting point?",
                    solutionText = "Solution: Distance = √(4² + 3²) = √(16 + 9) = √25 = 5 km.",
                    shortTrickText = "Use Pythagorean triplet (3, 4, 5)."
                )
            ),
            practiceQuestions = listOf(
                Question(
                    id = "reas_q5",
                    chapterId = "reas_03",
                    subjectType = SubjectType.REASONING,
                    questionText = "Ravi walks 8 km North, then turns East and walks 6 km. In which direction and at what distance is he from his starting point?",
                    optionA = "10 km, North-East",
                    optionB = "10 km, South-East",
                    optionC = "14 km, North",
                    optionD = "12 km, East",
                    correctOptionIndex = 0,
                    detailedSolution = "Distance = √(8² + 6²) = √(64 + 36) = √100 = 10 km. Direction: Between North and East = North-East. Option (A) is correct.",
                    difficultyLevel = DifficultyLevel.EASY
                )
            )
        )
    )

    val gkChapters: List<Chapter> = listOf(
        Chapter(
            id = "gk_01",
            subjectType = SubjectType.GENERAL_KNOWLEDGE,
            titleHindi = "1. Ancient Indian History",
            titleEnglish = "Ancient Indian History",
            chapterNumber = 1,
            description = "Indus Valley Civilization, Vedic Period, Buddhism, Jainism, Mauryan Empire & Gupta Dynasty.",
            conceptExplanation = """
                Key Milestones of Ancient Indian History:

                1. Indus Valley Civilization (2500 BC - 1750 BC):
                • Bronze Age urban civilization.
                • Harappa discovered by Dayaram Sahni in 1921.
                • Mohenjo-daro ('Mound of the Dead') discovered by R.D. Banerji in 1922. Great Bath located here.
                • Lothal (Gujarat) was a major ancient port town.

                2. Vedic Period (1500 BC - 600 BC):
                • 4 Vedas: Rigveda (Oldest, Gayatri Mantra in 3rd Mandala), Yajurveda (Prose & Verse), Samaveda (Origin of Indian Music), Atharvaveda (Medicine & Hymns).

                3. Buddhism & Jainism:
                • Gautama Buddha: Birth - Lumbini (Nepal), Enlightenment - Bodh Gaya, First Sermon - Sarnath (Dhammacakkappavattana), Parinirvana - Kushinagar.
                • Lord Mahavira: 24th Tirthankara of Jainism.

                4. Mauryan Empire:
                • Founder: Chandragupta Maurya with assistance of Chanakya (Kautilya). Chanakya authored 'Arthashastra'.
                • Emperor Ashoka: Adopted Buddhism after the Kalinga War in 261 BC.
            """.trimIndent(),
            revisionFacts = listOf(
                "Harappa Discoverer: Dayaram Sahni (1921)",
                "Mohenjo-daro meaning: Mound of the Dead",
                "Lothal: Ancient Indus Valley Dockyard/Port (Gujarat)",
                "Gayatri Mantra source: Rigveda (3rd Mandala)",
                "First Buddhist Council: Rajgir (under Ajatashatru)"
            ),
            practiceQuestions = listOf(
                Question(
                    id = "gk_q1",
                    chapterId = "gk_01",
                    subjectType = SubjectType.GENERAL_KNOWLEDGE,
                    questionText = "In which modern Indian state is the famous Indus Valley port city 'Lothal' located?",
                    optionA = "Gujarat",
                    optionB = "Rajasthan",
                    optionC = "Punjab",
                    optionD = "Haryana",
                    correctOptionIndex = 0,
                    detailedSolution = "Lothal was a prominent port city of the Harappan civilization, located along the Bhogava river in Gujarat. Option (A) is correct.",
                    difficultyLevel = DifficultyLevel.EASY
                ),
                Question(
                    id = "gk_q2",
                    chapterId = "gk_01",
                    subjectType = SubjectType.GENERAL_KNOWLEDGE,
                    questionText = "What is the primary subject matter of the ancient book 'Arthashastra' authored by Chanakya (Kautilya)?",
                    optionA = "Statecraft and Political Administration",
                    optionB = "Economics and Agriculture",
                    optionC = "Religious Text",
                    optionD = "Military Tactics",
                    correctOptionIndex = 0,
                    detailedSolution = "'Arthashastra' by Kautilya deals primarily with statecraft, governance, political administration, and foreign policy. Option (A) is correct.",
                    difficultyLevel = DifficultyLevel.EASY
                )
            )
        ),
        Chapter(
            id = "gk_02",
            subjectType = SubjectType.GENERAL_KNOWLEDGE,
            titleHindi = "2. Indian Polity & Constitution",
            titleEnglish = "Indian Polity & Constitution",
            chapterNumber = 2,
            description = "Constituent Assembly, Fundamental Rights, Duties, Parliament, President & Prime Minister.",
            conceptExplanation = """
                The Constitution of India is the longest written constitution in the world.

                1. Constituent Assembly & Framing:
                • First meeting: 9 December 1946 (Temporary President: Dr. Sachchidananda Sinha).
                • Permanent President: Dr. Rajendra Prasad (11 December 1946).
                • Drafting Committee Chairman: Dr. B.R. Ambedkar.
                • Adopted: 26 November 1949 | Came into force: 26 January 1950.

                2. Fundamental Rights (Part III, Articles 12 to 35):
                • 6 Fundamental Rights at present (Right to Property removed by 44th Amendment 1978; now a legal right under Art 300A).
                • Article 14: Equality before Law.
                • Article 17: Abolition of Untouchability.
                • Article 21: Protection of Life and Personal Liberty.
                • Article 21A: Right to Free and Compulsory Education for children aged 6 to 14 (86th Amendment 2002).
                • Article 32: Right to Constitutional Remedies (Dr. Ambedkar called it the 'Heart and Soul of the Constitution').

                3. Fundamental Duties (Part IV-A, Article 51A):
                • Added by 42nd Amendment 1976 on recommendation of Swaran Singh Committee (11 duties; borrowed from USSR).

                4. Parliament & Executive:
                • President (Article 52): First Citizen of India. Minimum age 35 years.
                • Vice-President (Article 63): Ex-Officio Chairman of Rajya Sabha.
                • Prime Minister (Article 75): Real executive head of government.
            """.trimIndent(),
            revisionFacts = listOf(
                "Constitution Enforcement Date: 26 January 1950",
                "Drafting Committee Chairman: Dr. B.R. Ambedkar",
                "Abolition of Untouchability: Article 17",
                "Heart & Soul of Constitution: Article 32",
                "Fundamental Duties: Article 51A (Part IV-A) - Borrowed from USSR"
            ),
            practiceQuestions = listOf(
                Question(
                    id = "gk_q3",
                    chapterId = "gk_02",
                    subjectType = SubjectType.GENERAL_KNOWLEDGE,
                    questionText = "Which Article of the Indian Constitution abolishes 'Untouchability' and forbids its practice?",
                    optionA = "Article 17",
                    optionB = "Article 19",
                    optionC = "Article 14",
                    optionD = "Article 21",
                    correctOptionIndex = 0,
                    detailedSolution = "Article 17 of the Indian Constitution abolishes untouchability and makes its practice in any form a punishable offense. Option (A) is correct.",
                    difficultyLevel = DifficultyLevel.EASY
                )
            )
        )
    )

    val currentAffairsChapters: List<Chapter> = listOf(
        Chapter(
            id = "ca_01",
            subjectType = SubjectType.CURRENT_AFFAIRS,
            titleHindi = "1. Current Affairs 2026-2027",
            titleEnglish = "Current Affairs 2026-2027",
            chapterNumber = 1,
            description = "National affairs, international updates, joint military exercises, sports awards & key appointments.",
            conceptExplanation = """
                Important Current Affairs updates for Exam (2026-2027):

                1. Joint Military Exercises:
                • Exercise Surya Kiran (India & Nepal).
                • Exercise Garuda / Shakti (India & France).
                • Exercise Sampriti (India & Bangladesh).
                • Exercise Mitra Shakti (India & Sri Lanka).
                • INS Vikrant: First indigenous aircraft carrier commissioned by Indian Navy.

                2. Sports Updates:
                • Indian medal winners at Asian Games and Olympic Games.
                • ICC T20 World Cup and ICC Champions Trophy highlights.
                • Major Dhyan Chand Khel Ratna Awardees.

                3. High-Profile Appointments:
                • Chief Justice of India (CJI).
                • Chief Election Commissioner of India.
                • Chiefs of Army, Navy, and Air Force.
                • Chairpersons of ISRO & DRDO.
            """.trimIndent(),
            revisionFacts = listOf(
                "DRDO Chairman: Dr. Samir V. Kamat",
                "Indigenous Aircraft Carrier: INS Vikrant",
                "Mitra Shakti Exercise: Conducted between India & Sri Lanka",
                "Surya Kiran Exercise: Conducted between India & Nepal"
            ),
            practiceQuestions = listOf(
                Question(
                    id = "ca_q1",
                    chapterId = "ca_01",
                    subjectType = SubjectType.CURRENT_AFFAIRS,
                    questionText = "The bilateral military exercise 'Mitra Shakti' is conducted between India and which country?",
                    optionA = "Sri Lanka",
                    optionB = "Nepal",
                    optionC = "Bangladesh",
                    optionD = "France",
                    correctOptionIndex = 0,
                    detailedSolution = "'Mitra Shakti' is an annual bilateral joint military training exercise conducted between the armies of India and Sri Lanka. Option (A) is correct.",
                    difficultyLevel = DifficultyLevel.EASY
                )
            )
        )
    )

    val mathsChapters: List<Chapter> = listOf(
        Chapter(
            id = "math_01",
            subjectType = SubjectType.MATHEMATICS,
            titleHindi = "1. Number System",
            titleEnglish = "Number System",
            chapterNumber = 1,
            description = "Natural, Whole & Prime numbers, divisibility tests, and unit digit calculation shortcuts.",
            conceptExplanation = """
                Number System forms the foundation of Elementary Mathematics.

                1. Classification of Numbers:
                • Natural Numbers: 1, 2, 3, 4, 5...
                • Whole Numbers: 0, 1, 2, 3, 4...
                • Even Numbers: Divisible by 2 (2, 4, 6, 8...).
                • Odd Numbers: Not divisible by 2 (1, 3, 5, 7...).
                • Prime Numbers: Numbers having exactly two factors (1 and the number itself). e.g., 2, 3, 5, 7, 11, 13, 17... (2 is the only even prime number).

                2. Divisibility Rules:
                • Divisible by 2: Last digit is 0, 2, 4, 6, or 8.
                • Divisible by 3: Sum of digits is divisible by 3.
                • Divisible by 4: Last two digits form a number divisible by 4.
                • Divisible by 5: Last digit is 0 or 5.
                • Divisible by 9: Sum of digits is divisible by 9.
                • Divisible by 11: Difference between sum of digits at odd places and even places is 0 or a multiple of 11.
            """.trimIndent(),
            formulas = listOf(
                FormulaBox(
                    title = "Sum of First n Natural Numbers",
                    formulaText = "Sum = [n × (n + 1)] / 2",
                    explanation = "Direct formula to find the sum of first n natural consecutive numbers."
                ),
                FormulaBox(
                    title = "Sum of First n Even Numbers",
                    formulaText = "Sum = n × (n + 1)",
                    explanation = "Direct formula for sum of first n even numbers."
                ),
                FormulaBox(
                    title = "Sum of First n Odd Numbers",
                    formulaText = "Sum = n²",
                    explanation = "The sum of first n odd natural numbers equals n squared."
                )
            ),
            shortTricks = listOf(
                "Unit Digit Trick: To find unit digit of a number raised to a large power, divide the exponent by 4 and take the remainder as the power."
            ),
            solvedExamples = listOf(
                SolvedExample(
                    id = 1,
                    questionText = "Q1. Find the sum of first 20 natural numbers?",
                    solutionText = "Solution: n = 20. Sum = [20 × (20 + 1)] / 2 = (20 × 21) / 2 = 210.",
                    shortTrickText = "Apply n(n+1)/2 formula directly: 20 × 21 / 2 = 210."
                )
            ),
            practiceQuestions = listOf(
                Question(
                    id = "math_q1",
                    chapterId = "math_01",
                    subjectType = SubjectType.MATHEMATICS,
                    questionText = "What is the sum of the first 50 natural numbers?",
                    optionA = "1275",
                    optionB = "1250",
                    optionC = "1300",
                    optionD = "1225",
                    correctOptionIndex = 0,
                    detailedSolution = "Formula: Sum = [n(n + 1)] / 2. Here n = 50. Sum = (50 × 51) / 2 = 25 × 51 = 1275. Option (A) is correct.",
                    difficultyLevel = DifficultyLevel.EASY
                ),
                Question(
                    id = "math_q2",
                    chapterId = "math_01",
                    subjectType = SubjectType.MATHEMATICS,
                    questionText = "Which is the smallest prime number?",
                    optionA = "2",
                    optionB = "1",
                    optionC = "3",
                    optionD = "0",
                    correctOptionIndex = 0,
                    detailedSolution = "2 is the smallest prime number and also the only even prime number. 1 is neither prime nor composite. Option (A) is correct.",
                    difficultyLevel = DifficultyLevel.EASY
                )
            )
        ),
        Chapter(
            id = "math_02",
            subjectType = SubjectType.MATHEMATICS,
            titleHindi = "2. Percentage",
            titleEnglish = "Percentage",
            chapterNumber = 2,
            description = "Percentage concepts, fraction conversions, increase/decrease, and shortcuts.",
            conceptExplanation = """
                Percentage means 'per hundred'. The symbol % denotes percentage.

                Converting Fraction to Percentage: Multiply fraction by 100. (e.g., 1/2 × 100 = 50%)
                Converting Percentage to Fraction: Divide by 100. (e.g., 25% = 25/100 = 1/4)

                Important Fraction-Percentage Equivalents:
                1/2 = 50% | 1/3 = 33.33% | 1/4 = 25% | 1/5 = 20% | 1/6 = 16.66% | 1/8 = 12.5% | 1/10 = 10%
            """.trimIndent(),
            formulas = listOf(
                FormulaBox(
                    title = "Percentage Change Formula",
                    formulaText = "% Change = (Change / Original Value) × 100",
                    explanation = "Percentage increase or decrease relative to initial base value."
                ),
                FormulaBox(
                    title = "Successive Percentage Formula",
                    formulaText = "Net % Change = a + b + (a × b)/100",
                    explanation = "Used for two successive percentage increases or decreases."
                )
            ),
            shortTricks = listOf(
                "If a value is increased by x% and then decreased by x%, there is always a net loss of x²/100 %."
            ),
            solvedExamples = listOf(
                SolvedExample(
                    id = 1,
                    questionText = "Q1. If the price of an item is increased by 20% and then reduced by 20%, what is the net percentage change?",
                    solutionText = "Solution: Loss % = x²/100 = (20)² / 100 = 400 / 100 = 4% decrease.",
                    shortTrickText = "Net change = 20 × 20 / 100 = 4% decrease."
                )
            ),
            practiceQuestions = listOf(
                Question(
                    id = "math_q3",
                    chapterId = "math_02",
                    subjectType = SubjectType.MATHEMATICS,
                    questionText = "Rahul's salary increases from ₹10,000 to ₹12,000. Find the percentage increase in his salary?",
                    optionA = "20%",
                    optionB = "15%",
                    optionC = "25%",
                    optionD = "10%",
                    correctOptionIndex = 0,
                    detailedSolution = "Increase = 12,000 - 10,000 = ₹2,000. % Increase = (2,000 / 10,000) × 100 = 20%. Option (A) is correct.",
                    difficultyLevel = DifficultyLevel.EASY
                )
            )
        )
    )

    val hindiChapters: List<Chapter> = listOf(
        Chapter(
            id = "hin_01",
            subjectType = SubjectType.HINDI_LANGUAGE,
            titleHindi = "1. English & General Language Skills",
            titleEnglish = "General English & Language Skills",
            chapterNumber = 1,
            description = "Nouns, Verbs, Vocabulary, Synonyms, Antonyms, Idioms & Sentence Correction.",
            conceptExplanation = """
                The Language section evaluates core grammar and comprehension skills.

                1. Key Grammar Topics:
                • Nouns & Pronouns: Naming words and replacement words.
                • Subject-Verb Agreement: Matching singular and plural verbs with subjects.
                • Tenses: Past, Present, and Future sentence construction.

                2. Vocabulary & Synonyms:
                • Solar / Sun: Solar, Helios, Bright.
                • Water: Aqua, H2O, Pure Water.

                3. Antonyms:
                • Ancient ↔ Modern | Truth ↔ Falsehood | Profit ↔ Loss.

                4. Idioms & Phrases:
                • 'Apple of one's eye' = Someone very dear.
                • 'Once in a blue moon' = Very rarely.
            """.trimIndent(),
            revisionFacts = listOf(
                "Apple of one's eye: Extremely dear or cherished",
                "Once in a blue moon: Very rarely occurring event",
                "Antonym of Ancient: Modern",
                "Synonym of Honest: Candid"
            ),
            practiceQuestions = listOf(
                Question(
                    id = "hin_q1",
                    chapterId = "hin_01",
                    subjectType = SubjectType.HINDI_LANGUAGE,
                    questionText = "What is the correct antonym for the word 'ANCIENT'?",
                    optionA = "Modern",
                    optionB = "Old",
                    optionC = "Historic",
                    optionD = "Past",
                    correctOptionIndex = 0,
                    detailedSolution = "'Ancient' means belonging to the distant past. Its opposite (antonym) is 'Modern'. Option (A) is correct.",
                    difficultyLevel = DifficultyLevel.EASY
                ),
                Question(
                    id = "hin_q2",
                    chapterId = "hin_01",
                    subjectType = SubjectType.HINDI_LANGUAGE,
                    questionText = "Choose the correct meaning of the idiom 'Apple of one's eye':",
                    optionA = "A very dear person",
                    optionB = "A fresh fruit",
                    optionC = "An eye disease",
                    optionD = "A red item",
                    correctOptionIndex = 0,
                    detailedSolution = "'Apple of one's eye' means a person or thing that is loved above all others. Option (A) is correct.",
                    difficultyLevel = DifficultyLevel.EASY
                )
            )
        )
    )

    val englishChapters: List<Chapter> = listOf(
        Chapter(
            id = "eng_01",
            subjectType = SubjectType.ENGLISH_LANGUAGE,
            titleHindi = "1. English Grammar & Vocabulary",
            titleEnglish = "English Grammar & Vocabulary",
            chapterNumber = 1,
            description = "Parts of speech, Tenses, Prepositions, Synonyms, Antonyms & One Word Substitution.",
            conceptExplanation = """
                English Language section for SSC GD:

                1. Important Grammar Rules:
                • Subject-Verb Agreement: Singular subject takes a singular verb, plural subject takes a plural verb. (e.g., "He runs fast", "They run fast").
                • Articles: 'A' used before consonant sounds, 'An' used before vowel sounds (a, e, i, o, u sound), 'The' before specific/unique nouns.

                2. Important Synonyms:
                • Abandon = Forsake, Leave, Desert.
                • Diligent = Hardworking, Industrious.
                • Candid = Honest, Frank, Straightforward.

                3. Important Antonyms:
                • Ancient ↔ Modern.
                • Expand ↔ Contract.
                • Transparent ↔ Opaque.

                4. One Word Substitutions:
                • One who looks at the bright side of things = Optimist.
                • One who looks at the dark side of things = Pessimist.
                • A person who knows everything = Omniscient.
            """.trimIndent(),
            revisionFacts = listOf(
                "Synonym of Diligent: Hardworking",
                "Antonym of Transparent: Opaque",
                "One who looks at bright side: Optimist",
                "One who knows everything: Omniscient"
            ),
            practiceQuestions = listOf(
                Question(
                    id = "eng_q1",
                    chapterId = "eng_01",
                    subjectType = SubjectType.ENGLISH_LANGUAGE,
                    questionText = "Select the correct Synonym for the word 'DILIGENT':",
                    optionA = "Hardworking",
                    optionB = "Lazy",
                    optionC = "Careless",
                    optionD = "Foolish",
                    correctOptionIndex = 0,
                    detailedSolution = "'Diligent' means showing care and effort in work. Its synonym is 'Hardworking'. Option (A) is correct.",
                    difficultyLevel = DifficultyLevel.EASY
                )
            )
        )
    )

    val baseMockTests: List<MockTest> = (1..20).map { testNum ->
        MockTest(
            id = testNum,
            examCategory = ExamCategory.SSC_GD,
            title = "SSC GD Full Mock Test #$testNum (Latest Pattern 160 Marks)",
            description = "80 Questions (20 Reasoning + 20 GK + 20 Maths + 20 English/Language) | Duration: 60 mins | Negative: -0.25",
            totalQuestions = 80,
            totalMarks = 160,
            durationMinutes = 60,
            questions = listOf(
                Question(
                    id = "m${testNum}_q1",
                    mockTestId = testNum,
                    subjectType = SubjectType.REASONING,
                    questionText = "Reasoning Q1: If 7 : 49 :: 9 : ?",
                    optionA = "81",
                    optionB = "63",
                    optionC = "72",
                    optionD = "90",
                    correctOptionIndex = 0,
                    detailedSolution = "7² = 49, therefore 9² = 81. Correct option is (A) 81.",
                    difficultyLevel = DifficultyLevel.EASY
                ),
                Question(
                    id = "m${testNum}_q2",
                    mockTestId = testNum,
                    subjectType = SubjectType.GENERAL_KNOWLEDGE,
                    questionText = "General Knowledge Q2: Who was the first President of independent India?",
                    optionA = "Dr. Rajendra Prasad",
                    optionB = "Dr. S. Radhakrishnan",
                    optionC = "Pandit Jawaharlal Nehru",
                    optionD = "Sardar Vallabhbhai Patel",
                    correctOptionIndex = 0,
                    detailedSolution = "Dr. Rajendra Prasad served as the first President of India from 1950 to 1962. Correct answer is (A).",
                    difficultyLevel = DifficultyLevel.EASY
                ),
                Question(
                    id = "m${testNum}_q3",
                    mockTestId = testNum,
                    subjectType = SubjectType.MATHEMATICS,
                    questionText = "Mathematics Q3: What will be the Selling Price of an article marked at ₹500 after giving a 10% discount?",
                    optionA = "₹450",
                    optionB = "₹400",
                    optionC = "₹480",
                    optionD = "₹490",
                    correctOptionIndex = 0,
                    detailedSolution = "Discount = 500 × 10% = ₹50. Selling Price = 500 - 50 = ₹450. Correct option is (A) ₹450.",
                    difficultyLevel = DifficultyLevel.EASY
                ),
                Question(
                    id = "m${testNum}_q4",
                    mockTestId = testNum,
                    subjectType = SubjectType.ENGLISH_LANGUAGE,
                    questionText = "Language Q4: Which of the following is a synonym for 'TRANSPARENT'?",
                    optionA = "Clear",
                    optionB = "Opaque",
                    optionC = "Dark",
                    optionD = "Heavy",
                    correctOptionIndex = 0,
                    detailedSolution = "'Transparent' means allowing light to pass through so objects can be distinctly seen; its synonym is 'Clear'. Option (A) is correct.",
                    difficultyLevel = DifficultyLevel.EASY
                )
            )
        )
    }

    val revisionFactCategoryList: List<RevisionFact> = listOf(
        RevisionFact(
            id = "rf_01",
            category = "Elementary Mathematics Formulas",
            title = "Algebra, Geometry & Mensuration Key Formulas",
            content = "Essential mathematics formula collection for SSC GD Constable exam.",
            bulletPoints = listOf(
                "(a + b)² = a² + b² + 2ab",
                "(a - b)² = a² + b² - 2ab",
                "a² - b² = (a + b)(a - b)",
                "Area of Rectangle = Length × Breadth",
                "Area of Square = Side²",
                "Area of Circle = πr² (where π = 22/7)",
                "Circumference of Circle = 2πr",
                "Simple Interest (SI) = (Principal × Rate × Time) / 100",
                "Compound Interest Amount (A) = P(1 + R/100)ᵀ"
            )
        ),
        RevisionFact(
            id = "rf_02",
            category = "Indian Polity & Constitution",
            title = "Key Articles & Constitutional Amendments",
            content = "Frequently asked polity and constitution facts.",
            bulletPoints = listOf(
                "Article 14: Equality before law and equal protection of laws",
                "Article 17: Abolition of Untouchability",
                "Article 21: Protection of life and personal liberty",
                "Article 21A: Free & compulsory education for children 6-14 years",
                "Article 32: Constitutional Remedies (Heart & Soul of Constitution)",
                "Article 44: Uniform Civil Code for citizens",
                "Article 51A: Fundamental Duties (11 duties; Part IV-A)",
                "Article 61: Procedure for Impeachment of the President",
                "Article 72: Power of President to grant pardons",
                "Article 110: Definition of Money Bills"
            )
        ),
        RevisionFact(
            id = "rf_03",
            category = "General Science Quick Facts",
            title = "Physics, Chemistry & Biology One-Liners",
            content = "High-yield science facts for revision.",
            bulletPoints = listOf(
                "Vitamin C chemical name: Ascorbic Acid",
                "Deficiency of Vitamin C causes: Scurvy",
                "Deficiency of Vitamin A causes: Night Blindness",
                "Deficiency of Vitamin D causes: Rickets",
                "Largest gland in the human body: Liver",
                "Smallest bone in the human body: Stapes (Ear)",
                "Longest bone in the human body: Femur (Thigh)",
                "Normal pH of human blood: 7.4 (Slightly alkaline)",
                "Common Salt chemical name: Sodium Chloride (NaCl)",
                "Baking Soda chemical name: Sodium Bicarbonate (NaHCO₃)"
            )
        ),
        RevisionFact(
            id = "rf_04",
            category = "Indian History & Geography",
            title = "History & Geography Quick Revision",
            content = "Exam-oriented static GK facts.",
            bulletPoints = listOf(
                "Longest river in India: Ganga (2,525 km)",
                "Highest dam in India: Tehri Dam (Uttarakhand - Bhagirathi River)",
                "Longest dam in India: Hirakud Dam (Odisha - Mahanadi River)",
                "Start of Revolt of 1857: 10th May 1857 (Meerut)",
                "Jallianwala Bagh Massacre: 13th April 1919 (Amritsar)",
                "Non-Cooperation Movement launched: 1920 (Mahatma Gandhi)",
                "Dandi March (Salt Satyagraha): 12th March 1930",
                "Quit India Movement: 8th August 1942 ('Do or Die' slogan)"
            )
        )
    )

    fun getAllChapters(): List<Chapter> {
        val baseList = introChapters + reasoningChapters + ExpandedBookData.additionalReasoningChapters + gkChapters + currentAffairsChapters + mathsChapters + ExpandedBookData.additionalMathsChapters + hindiChapters + englishChapters
        
        val agniveerList = baseList.map { 
            it.copy(
                id = it.id + "_ag", 
                examCategory = ExamCategory.AGNIVEER,
                titleEnglish = it.titleEnglish.replace("SSC GD", "Agniveer"),
                titleHindi = it.titleHindi.replace("SSC GD", "Agniveer")
            ) 
        }
        
        val taList = baseList.map { 
            it.copy(
                id = it.id + "_ta", 
                examCategory = ExamCategory.TERRITORIAL_ARMY,
                titleEnglish = it.titleEnglish.replace("SSC GD", "Territorial Army"),
                titleHindi = it.titleHindi.replace("SSC GD", "Territorial Army")
            ) 
        }
        
        return baseList + agniveerList + taList
    }

    fun getAllMockTests(): List<MockTest> {
        val agniveerList = baseMockTests.map { 
            it.copy(
                id = it.id + 100, 
                examCategory = ExamCategory.AGNIVEER,
                title = it.title.replace("SSC GD", "Agniveer")
            )
        }
        val taList = baseMockTests.map { 
            it.copy(
                id = it.id + 200, 
                examCategory = ExamCategory.TERRITORIAL_ARMY,
                title = it.title.replace("SSC GD", "Territorial Army")
            )
        }
        return baseMockTests + agniveerList + taList
    }

    fun getChaptersBySubject(subject: SubjectType): List<Chapter> {
        return getAllChapters().filter { it.subjectType == subject }
    }

    fun getChapterById(id: String): Chapter? {
        return getAllChapters().firstOrNull { it.id == id }
    }
}
