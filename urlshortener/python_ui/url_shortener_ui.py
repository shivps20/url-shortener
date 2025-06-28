import streamlit as st
import requests

API_BASE = "http://localhost:8080/api/url"

st.title("URL Shortener UI")

tab1, tab2 = st.tabs(["Shorten URL", "Get Original URL"])

with tab1:
    st.header("Shorten a URL")
    original_url = st.text_input("Original URL")
    custom_alias = st.text_input("Custom Alias (optional)")
    expires_at = st.text_input("Expiry (optional, e.g. 2025-07-31T23:59:59)")

    if st.button("Shorten URL"):
        payload = {"originalUrl": original_url}
        if custom_alias:
            payload["customAlias"] = custom_alias
        if expires_at:
            payload["expiresAt"] = expires_at
        response = requests.post(f"{API_BASE}/shorten", json=payload)
        if response.ok:
            st.success(f"Short URL: {response.json()['shortUrl']}")
            st.json(response.json())
        else:
            st.error(f"Error: {response.text}")

with tab2:
    st.header("Get Original URL")
    short_url = st.text_input("Short URL code or alias")
    if st.button("Get Original URL"):
        params = {"shortUrl": short_url}
        response = requests.get(f"{API_BASE}/original", params=params)
        if response.ok:
            st.success(f"Original URL: {response.json()['originalUrl']}")
        else:
            st.error(f"Error: {response.text}")