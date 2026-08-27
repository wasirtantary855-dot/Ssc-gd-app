package com.example.data.provider

import com.example.data.model.SubjectType
import com.example.data.model.VideoLesson

object VideoDataProvider {

    fun extractYouTubeId(urlOrId: String): String {
        val trimmed = urlOrId.trim()
        if (trimmed.length == 11 && !trimmed.contains("/") && !trimmed.contains("?") && !trimmed.contains("&") && !trimmed.contains("=")) {
            return trimmed
        }
        
        // Handle https://www.youtube.com/watch?v=CHAykHhzjnA
        val watchRegex = Regex("[?&]v=([^&#]+)")
        watchRegex.find(trimmed)?.let {
            if (it.groupValues.size > 1) return it.groupValues[1]
        }

        // Handle https://youtu.be/CHAykHhzjnA
        val shortRegex = Regex("youtu\\.be/([^?&#]+)")
        shortRegex.find(trimmed)?.let {
            if (it.groupValues.size > 1) return it.groupValues[1]
        }

        // Handle embed / v / shorts
        val pathRegex = Regex("(?:embed|v|shorts)/([^?&#]+)")
        pathRegex.find(trimmed)?.let {
            if (it.groupValues.size > 1) return it.groupValues[1]
        }

        return if (trimmed.length >= 11) trimmed.take(11) else trimmed
    }

    fun getThumbnailUrl(videoId: String): String {
        val cleanId = extractYouTubeId(videoId)
        return "https://img.youtube.com/vi/$cleanId/hqdefault.jpg"
    }

    fun getFallbackThumbnailUrl(videoId: String): String {
        val cleanId = extractYouTubeId(videoId)
        return "https://img.youtube.com/vi/$cleanId/mqdefault.jpg"
    }

    val defaultVideos: List<VideoLesson> = listOf(
        // REASONING
        VideoLesson(
            id = "vid_reas_01",
            subjectType = SubjectType.REASONING,
            topicName = "Analogy",
            title = "SSC GD Reasoning Classes 2026 - Analogy Masterclass & Shortcuts",
            youtubeVideoId = "CHAykHhzjnA",
            youtubeUrl = "https://www.youtube.com/watch?v=CHAykHhzjnA",
            duration = "52 mins",
            instructor = "SSC GD Exam Faculty",
            description = "Master Word Analogy, Number Analogy, and Letter Analogy with shortcut elimination techniques for SSC GD 2026.",
            linkedChapterId = "reas_01"
        ),
        VideoLesson(
            id = "vid_reas_02",
            subjectType = SubjectType.REASONING,
            topicName = "Classification",
            title = "Classification & Odd One Out Super Tricks for SSC GD",
            youtubeVideoId = "g7JvHk8L3mQ",
            youtubeUrl = "https://www.youtube.com/watch?v=CHAykHhzjnA",
            duration = "45 mins",
            instructor = "Reasoning Expert Team",
            description = "Learn how to quickly identify odd words, numbers, and letter pairs with 100% accuracy.",
            linkedChapterId = "reas_02"
        ),
        VideoLesson(
            id = "vid_reas_03",
            subjectType = SubjectType.REASONING,
            topicName = "Number Series",
            title = "Number Series & Missing Number Patterns Complete Lecture",
            youtubeVideoId = "zV5UvL1wBvY",
            youtubeUrl = "https://www.youtube.com/watch?v=CHAykHhzjnA",
            duration = "58 mins",
            instructor = "SSC GD Reasoning Master",
            description = "Learn arithmetic progressions, geometric steps, alternate series, and square-cube difference patterns.",
            linkedChapterId = "reas_03"
        ),
        VideoLesson(
            id = "vid_reas_04",
            subjectType = SubjectType.REASONING,
            topicName = "Coding-Decoding",
            title = "Coding-Decoding Shortcuts, EJOTY Formula & Position Rules",
            youtubeVideoId = "9yFvP8gWp5c",
            youtubeUrl = "https://www.youtube.com/watch?v=CHAykHhzjnA",
            duration = "50 mins",
            instructor = "Top GD Educator",
            description = "Master forward-backward positional shifts, opposite letters (A-Z, B-Y, C-X), and substitution coding.",
            linkedChapterId = "reas_04"
        ),
        VideoLesson(
            id = "vid_reas_05",
            subjectType = SubjectType.REASONING,
            topicName = "Blood Relations",
            title = "Blood Relations Family Tree & Generation Chart Technique",
            youtubeVideoId = "3u4TjG8nK7k",
            youtubeUrl = "https://www.youtube.com/watch?v=CHAykHhzjnA",
            duration = "42 mins",
            instructor = "Reasoning Faculty",
            description = "Draw family trees in seconds using +/- gender markers and horizontal/vertical relationship tiers.",
            linkedChapterId = "reas_05"
        ),
        VideoLesson(
            id = "vid_reas_06",
            subjectType = SubjectType.REASONING,
            topicName = "Direction & Distance",
            title = "Direction & Distance Angles, Turns & Pythagoras Rules",
            youtubeVideoId = "Mv8xGv8HhJk",
            youtubeUrl = "https://www.youtube.com/watch?v=CHAykHhzjnA",
            duration = "44 mins",
            instructor = "SSC GD Exam Faculty",
            description = "Understand 8-direction compass, clockwise/anticlockwise rotations, shadow at sunrise/sunset, and shortest distance.",
            linkedChapterId = "reas_06"
        ),
        VideoLesson(
            id = "vid_reas_07",
            subjectType = SubjectType.REASONING,
            topicName = "Venn Diagram",
            title = "Logical Venn Diagrams & Syllogism Fundamentals",
            youtubeVideoId = "p9K8L7jH6mE",
            youtubeUrl = "https://www.youtube.com/watch?v=CHAykHhzjnA",
            duration = "40 mins",
            instructor = "SSC GD Reasoning Master",
            description = "Master overlapping sets, mutually exclusive categories, and subset relations with real exam examples.",
            linkedChapterId = "reas_07"
        ),

        // MATHEMATICS
        VideoLesson(
            id = "vid_math_01",
            subjectType = SubjectType.MATHEMATICS,
            topicName = "Percentage",
            title = "Percentage Fast Calculation, Fraction Values & Base Concepts",
            youtubeVideoId = "gJ9V8W5qL2k",
            youtubeUrl = "https://www.youtube.com/watch?v=CHAykHhzjnA",
            duration = "1 hr 10 mins",
            instructor = "Maths Wizard",
            description = "Learn fraction-to-percentage conversion tables, successive percentage formula, and price-consumption balance.",
            linkedChapterId = "math_02"
        ),
        VideoLesson(
            id = "vid_math_02",
            subjectType = SubjectType.MATHEMATICS,
            topicName = "Profit & Loss",
            title = "Profit, Loss & Discount Formulas, Mark-up & Solved Questions",
            youtubeVideoId = "a8N7vB6c5xD",
            youtubeUrl = "https://www.youtube.com/watch?v=CHAykHhzjnA",
            duration = "1 hr 05 mins",
            instructor = "Quantitative Aptitude Faculty",
            description = "Master Cost Price, Selling Price, Marked Price, successive discounts, and dishonest shopkeeper shortcuts.",
            linkedChapterId = "math_03"
        ),
        VideoLesson(
            id = "vid_math_03",
            subjectType = SubjectType.MATHEMATICS,
            topicName = "Ratio & Proportion",
            title = "Ratio & Proportion Concepts, Cross Multiplication & Coin Problems",
            youtubeVideoId = "e4R7tY8u9I0",
            youtubeUrl = "https://www.youtube.com/watch?v=CHAykHhzjnA",
            duration = "55 mins",
            instructor = "SSC GD Maths Mentor",
            description = "Learn combined ratio (A:B:C), mean proportional, third/fourth proportional, and mixture ratios.",
            linkedChapterId = "math_04"
        ),
        VideoLesson(
            id = "vid_math_04",
            subjectType = SubjectType.MATHEMATICS,
            topicName = "Average",
            title = "Average Concept, Replacement Rules & Group Age Shortcuts",
            youtubeVideoId = "k1M2n3B4v5C",
            youtubeUrl = "https://www.youtube.com/watch?v=CHAykHhzjnA",
            duration = "48 mins",
            instructor = "Maths Expert",
            description = "Solve average speed, bowling averages, student score corrections, and inclusion-exclusion without lengthy equations.",
            linkedChapterId = "math_05"
        ),
        VideoLesson(
            id = "vid_math_05",
            subjectType = SubjectType.MATHEMATICS,
            topicName = "Time & Work",
            title = "Time and Work Efficiency, Alternate Days & Pipe-Cistern Methods",
            youtubeVideoId = "q7W8e9R0t1Y",
            youtubeUrl = "https://www.youtube.com/watch?v=CHAykHhzjnA",
            duration = "1 hr 15 mins",
            instructor = "Quantitative Specialist",
            description = "Master LCM total work method, men-women-boys equivalences (M1D1H1/W1 = M2D2H2/W2), and work wages.",
            linkedChapterId = "math_06"
        ),
        VideoLesson(
            id = "vid_math_06",
            subjectType = SubjectType.MATHEMATICS,
            topicName = "Time, Speed & Distance",
            title = "Time, Speed & Distance, Train Crossing & Boat-Stream Tricks",
            youtubeVideoId = "u2I3o4P5a6S",
            youtubeUrl = "https://www.youtube.com/watch?v=CHAykHhzjnA",
            duration = "1 hr 08 mins",
            instructor = "Maths Faculty",
            description = "Learn unit conversions (km/h to m/s), relative speed for trains, pole vs platform crossing, and upstream/downstream boats.",
            linkedChapterId = "math_07"
        ),

        // GK & GENERAL AWARENESS
        VideoLesson(
            id = "vid_gk_01",
            subjectType = SubjectType.GENERAL_KNOWLEDGE,
            topicName = "Indian History",
            title = "Indian History Crash Course: Indus Valley to Freedom Struggle",
            youtubeVideoId = "d4F5g6H7j8K",
            youtubeUrl = "https://www.youtube.com/watch?v=CHAykHhzjnA",
            duration = "1 hr 30 mins",
            instructor = "Static GK Master",
            description = "High-yield summary of Harappan civilization, Buddhism/Jainism, Mauryan & Mughal empires, and the 1857-1947 national movement.",
            linkedChapterId = "gk_01"
        ),
        VideoLesson(
            id = "vid_gk_02",
            subjectType = SubjectType.GENERAL_KNOWLEDGE,
            topicName = "Indian Geography",
            title = "Indian Geography: Rivers, Mountains, Passes & National Parks",
            youtubeVideoId = "h1J2k3L4z5X",
            youtubeUrl = "https://www.youtube.com/watch?v=CHAykHhzjnA",
            duration = "1 hr 12 mins",
            instructor = "Geography Mentor",
            description = "Detailed map walkthrough of Himalayan ranges, peninsular rivers (Ganga, Godavari), soil classifications, and climate zones.",
            linkedChapterId = "gk_02"
        ),
        VideoLesson(
            id = "vid_gk_03",
            subjectType = SubjectType.GENERAL_KNOWLEDGE,
            topicName = "Indian Polity",
            title = "Indian Constitution, Fundamental Rights & Important Articles",
            youtubeVideoId = "c6V7b8N9m0Q",
            youtubeUrl = "https://www.youtube.com/watch?v=CHAykHhzjnA",
            duration = "1 hr 20 mins",
            instructor = "Polity Expert",
            description = "Understand Preamble, Articles 12-35 (Fundamental Rights), DPSP (Articles 36-51), President powers, and Constitutional Amendments.",
            linkedChapterId = "gk_03"
        ),
        VideoLesson(
            id = "vid_gk_04",
            subjectType = SubjectType.GENERAL_KNOWLEDGE,
            topicName = "General Science",
            title = "General Science Top 100 Physics, Chemistry & Biology Facts",
            youtubeVideoId = "w1E2r3T4y5U",
            youtubeUrl = "https://www.youtube.com/watch?v=CHAykHhzjnA",
            duration = "55 mins",
            instructor = "Science Educator",
            description = "Frequently asked questions on SI units, human anatomy, vitamins & deficiency diseases, and periodic table elements.",
            linkedChapterId = "gk_04"
        ),
        VideoLesson(
            id = "vid_gk_05",
            subjectType = SubjectType.CURRENT_AFFAIRS,
            topicName = "Current Affairs",
            title = "Current Affairs 2025-2026: Sports, Awards, Summits & Schemes",
            youtubeVideoId = "i6O7p8A9s0D",
            youtubeUrl = "https://www.youtube.com/watch?v=CHAykHhzjnA",
            duration = "1 hr 00 min",
            instructor = "Daily GK Team",
            description = "Comprehensive review of Olympic/National games, Bharat Ratna, military exercises, and Union Government schemes.",
            linkedChapterId = "ca_01"
        ),

        // ENGLISH
        VideoLesson(
            id = "vid_eng_01",
            subjectType = SubjectType.ENGLISH_LANGUAGE,
            topicName = "Tenses",
            title = "English Tenses Masterclass & Common Error Identification",
            youtubeVideoId = "f1G2h3J4k5L",
            youtubeUrl = "https://www.youtube.com/watch?v=CHAykHhzjnA",
            duration = "48 mins",
            instructor = "English Language Faculty",
            description = "Complete guide to Present, Past, and Future tenses, continuous vs perfect aspects, and time markers in sentences.",
            linkedChapterId = "eng_01"
        ),
        VideoLesson(
            id = "vid_eng_02",
            subjectType = SubjectType.ENGLISH_LANGUAGE,
            topicName = "Articles",
            title = "Articles (A, An, The) Rules, Sound Rules & Spotting Errors",
            youtubeVideoId = "z2X3c4V5b6N",
            youtubeUrl = "https://www.youtube.com/watch?v=CHAykHhzjnA",
            duration = "38 mins",
            instructor = "Grammar Specialist",
            description = "Understand indefinite vs definite articles, vowel sound pronunciation rules (an honest, a university), and omission of articles.",
            linkedChapterId = "eng_02"
        ),
        VideoLesson(
            id = "vid_eng_03",
            subjectType = SubjectType.ENGLISH_LANGUAGE,
            topicName = "Prepositions",
            title = "Fixed Prepositions & Fill in the Blanks Rules for SSC GD",
            youtubeVideoId = "m7Q8w9E0r1T",
            youtubeUrl = "https://www.youtube.com/watch?v=CHAykHhzjnA",
            duration = "46 mins",
            instructor = "English Master",
            description = "Master high-frequency fixed prepositions (good at, interested in, proud of, abstain from) and prepositions of time/place.",
            linkedChapterId = "eng_03"
        ),
        VideoLesson(
            id = "vid_eng_04",
            subjectType = SubjectType.ENGLISH_LANGUAGE,
            topicName = "Synonyms & Antonyms",
            title = "Top Repeated Synonyms & Antonyms for SSC GD Exam",
            youtubeVideoId = "y2U3i4O5p6A",
            youtubeUrl = "https://www.youtube.com/watch?v=CHAykHhzjnA",
            duration = "54 mins",
            instructor = "Vocabulary Mentor",
            description = "Learn 200+ high-frequency vocabulary words with root words, prefixes/suffixes, and contextual sentence examples.",
            linkedChapterId = "eng_04"
        ),
        VideoLesson(
            id = "vid_eng_05",
            subjectType = SubjectType.ENGLISH_LANGUAGE,
            topicName = "Reading Comprehension",
            title = "Reading Comprehension & Cloze Test Quick Solving Tactics",
            youtubeVideoId = "s7D8f9G0h1J",
            youtubeUrl = "https://www.youtube.com/watch?v=CHAykHhzjnA",
            duration = "42 mins",
            instructor = "English Language Faculty",
            description = "Techniques to read passages quickly, locate central ideas, eliminate incorrect options, and solve cloze tests effortlessly.",
            linkedChapterId = "eng_05"
        )
    )
}
