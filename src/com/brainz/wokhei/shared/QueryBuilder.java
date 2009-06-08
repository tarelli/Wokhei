package com.brainz.wokhei.shared;

import java.util.List;

public class QueryBuilder {

	/**
	 * Refactor this shit into a better class
	 * @param obj
	 * @param filterQuery
	 * @param fieldName
	 * @param paramQuery
	 * @param paramName
	 * @param argList
	 */

	public static String _filters;
	public static String _paramDeclarations;

	public static void AddObjectToFilterAndParamForQuery(Object obj, String operator, String fieldName, String paramName, List<Object> argList)
	{
		if (_filters!="")
		{
			_filters += " && ";
		}

		if (_paramDeclarations!="")
		{
			_paramDeclarations += " , ";
		}

		//build user filter
		_filters += " ( " + fieldName + " " + operator + " " + paramName + " ) ";
		//build status ParamDeclaration
		_paramDeclarations += " " + obj.getClass().getName() + " " + paramName + " ";

		argList.add(obj);
	}

}
