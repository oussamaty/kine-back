# Use an official nginx image
FROM nginx:alpine

# Copy the built files into the nginx directory
COPY ./ /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Start nginx
CMD ["nginx", "-g", "daemon off;"]
