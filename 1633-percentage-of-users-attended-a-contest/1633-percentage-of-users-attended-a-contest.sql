# Write your MySQL query statement below

WITH total_users AS (
    SELECT COUNT(*) AS total
    FROM Users
)
SELECT
    r.contest_id,
    ROUND(COUNT(r.user_id) * 100.0 / t.total, 2) AS percentage
FROM Register r
CROSS JOIN total_users t
GROUP BY r.contest_id
ORDER BY percentage DESC, r.contest_id ASC;

