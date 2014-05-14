--
-- Name: string_agg(text); Type: FUNCTION; Schema: tm_cz; Owner: -
--
CREATE OR REPLACE FUNCTION string_agg (
       p_input IN text
	-- text_delimiter IN VARCHAR2
)
RETURNS varchar AS $body$

PARALLEL_ENABLE AGGREGATE USING t_string_agg;
