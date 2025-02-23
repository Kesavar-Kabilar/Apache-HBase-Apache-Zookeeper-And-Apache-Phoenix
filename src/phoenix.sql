--set the output format of the result set to TSV (Tab-Separated Values) to compare the query results obtained, with the autograder.
--Make sure to leave the following line as the first line of your final solution!
!outputformat tsv

-- A) Create Phoenix View
CREATE VIEW "powers" (
    "row1" VARCHAR PRIMARY KEY,
    "personal"."hero" VARCHAR,
    "personal"."power" VARCHAR,
    "professional"."name" VARCHAR,
    "professional"."xp" INTEGER,
    "custom"."color" VARCHAR
) AS SELECT * FROM "powers";

-- -- B) SQL JOIN Query
SELECT 
    p1."professional"."name" AS "Name1", 
    p2."professional"."name" AS "Name2", 
    p1."personal"."power" AS "Power"
FROM
    "powers" p1, "powers" p2
WHERE 
    p1."personal"."hero" LIKE 'yes' AND 
    p2."personal"."hero" LIKE 'yes' AND 
    p1."personal"."power" LIKE p2."personal"."power";